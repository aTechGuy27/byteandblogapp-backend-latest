package com.byteandblog.api.service;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.byteandblog.api.dto.NewsArticleDto;
import com.byteandblog.api.dto.NewsResponseDto;

@Service
public class NewsService {
	
	private static final Logger log = LoggerFactory.getLogger(NewsService.class);

    private final RestTemplate restTemplate;
    
    public NewsService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Value("${application.news.base-url}")
    private String newsBaseUrl;

    @Value("${application.news.default-feed-id}")
    private String defaultFeedId;

    public NewsResponseDto getTopHeadlines(int page, int pageSize) {
        return getTopHeadlines(page, pageSize, defaultFeedId);
    }

    public NewsResponseDto getTopHeadlines(int page, int pageSize, String feedId) {
        log.debug("Getting top headlines from NPR RSS feed, page: {}, pageSize: {}, feedId: {}", page, pageSize, feedId);
        
        try {
            String url = UriComponentsBuilder.fromHttpUrl(newsBaseUrl)
                    .queryParam("id", feedId)
                    .toUriString();
            
            log.info("Fetching news from URL: {}", url);
            String xmlResponse = restTemplate.getForObject(url, String.class);
            
            if (xmlResponse == null) {
                log.warn("Null response from NPR RSS feed");
                return createEmptyResponse();
            }
            
            log.debug("Received XML response of length: {}", xmlResponse.length());
            return parseRssFeed(xmlResponse, page, pageSize);
        } catch (Exception e) {
            log.error("Error fetching news from NPR RSS feed: {}", e.getMessage(), e);
            return createEmptyResponse();
        }
    }

    private NewsResponseDto parseRssFeed(String xmlContent, int page, int pageSize) {
        try {
            log.debug("Parsing RSS feed content");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Disable DTD processing to prevent XXE attacks
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlContent));
            Document doc = builder.parse(is);
            
            // Normalize the document to handle whitespace correctly
            doc.getDocumentElement().normalize();
            
            log.debug("XML document root element: {}", doc.getDocumentElement().getNodeName());
            
            // NPR RSS feed uses standard RSS format with <item> elements
            NodeList items = doc.getElementsByTagName("item");
            int totalResults = items.getLength();
            
            log.info("Found {} news items in the RSS feed", totalResults);
            
            // Calculate pagination
            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalResults);
            
            List<NewsArticleDto> articles = new ArrayList<>();
            
         // Pattern to extract image URL from content:encoded
            Pattern imagePattern = Pattern.compile("<img\\s+[^>]*src=[\"'](.*?\\.(png|jpe?g|gif|webp|bmp))(\\?.*?)?[\"']", Pattern.CASE_INSENSITIVE);
            
            for (int i = startIndex; i < endIndex; i++) {
                if (i >= totalResults) break;
                
                Element item = (Element) items.item(i);
                
                String title = getElementValue(item, "title");
                String description = getElementValue(item, "description");
                String link = getElementValue(item, "link");
                String pubDate = getElementValue(item, "pubDate");
                String contentEncoded = getElementValue(item, "content:encoded");
                String urlToImage = null;
                if (contentEncoded != null) {
                    Matcher matcher = imagePattern.matcher(contentEncoded);
                    if (matcher.find()) {
                        urlToImage = matcher.group(1);
                    }
                }
                
                log.debug("Processing news item: {}", title);
                
                LocalDateTime publishedAt = parseDate(pubDate);
                
                NewsArticleDto.NewsSourceDto source = NewsArticleDto.NewsSourceDto.builder()
                        .name("NPR")
                        .build();
                
                NewsArticleDto articleDto = NewsArticleDto.builder()
                        .title(title)
                        .description(description)
                        .url(link)
                        .publishedAt(publishedAt)
                        .source(source)
                        .imageUrl(urlToImage)
                        .build();
                
                articles.add(articleDto);
            }
            
            log.info("Successfully parsed {} news articles", articles.size());
            
            return NewsResponseDto.builder()
                    .articles(articles)
                    .totalResults(totalResults)
                    .build();
            
        } catch (Exception e) {
            log.error("Error parsing RSS feed: {}", e.getMessage(), e);
            return createEmptyResponse();
        }
    }
    
    private String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
    
    private LocalDateTime parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            log.warn("Empty date string");
            return LocalDateTime.now();
        }
        
        try {
            // RFC 822 date format used by RSS
            DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
            return ZonedDateTime.parse(dateStr, formatter).toLocalDateTime();
        } catch (DateTimeParseException e) {
            log.warn("Could not parse date: {} - {}", dateStr, e.getMessage());
            
            // Try alternative date formats
            try {
                // Some feeds use ISO format
                return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
            } catch (DateTimeParseException e2) {
                log.warn("Could not parse date with ISO format either: {}", dateStr);
                return LocalDateTime.now();
            }
        }
    }

    private NewsResponseDto createEmptyResponse() {
        return NewsResponseDto.builder()
                .articles(new ArrayList<>())
                .totalResults(0)
                .build();
    }
}
