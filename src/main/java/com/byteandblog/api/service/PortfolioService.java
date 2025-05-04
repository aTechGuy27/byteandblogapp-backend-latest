package com.byteandblog.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.byteandblog.api.dto.PortfolioItemDto;
import com.byteandblog.api.dto.PortfolioItemRequest;
import com.byteandblog.api.exception.ResourceNotFoundException;
import com.byteandblog.api.model.PortfolioItem;
import com.byteandblog.api.model.User;
import com.byteandblog.api.repository.PortfolioItemRepository;

@Service
public class PortfolioService {

	private static final Logger log = LoggerFactory.getLogger(PortfolioService.class);

	private final PortfolioItemRepository portfolioItemRepository;
	private final UserService userService;

	public PortfolioService(PortfolioItemRepository portfolioItemRepository, UserService userService) {
		this.portfolioItemRepository = portfolioItemRepository;
		this.userService = userService;
	}

	public List<PortfolioItemDto> getAllPortfolioItems() {
		log.debug("Getting all portfolio items");

		List<PortfolioItem> items = portfolioItemRepository.findAll();

		return items.stream().map(PortfolioItemDto::fromEntity).collect(Collectors.toList());
	}

	public PortfolioItemDto getPortfolioItemById(Long id) {
		log.debug("Getting portfolio item by id: {}", id);

		PortfolioItem item = portfolioItemRepository.findById(id).orElseThrow(() -> {
			log.warn("Portfolio item not found with id: {}", id);
			return new ResourceNotFoundException("Portfolio item not found with id: " + id);
		});

		return PortfolioItemDto.fromEntity(item);
	}

	public List<PortfolioItemDto> getPortfolioHighlights(int limit) {
		log.debug("Getting portfolio highlights, limit: {}", limit);

		List<PortfolioItem> items = portfolioItemRepository.findTopByOrderByCreatedAtDesc(limit);

		return items.stream().map(PortfolioItemDto::fromEntity).collect(Collectors.toList());
	}

	@Transactional
	public PortfolioItemDto createPortfolioItem(PortfolioItemRequest request, String userEmail) {
		log.info("Creating new portfolio item: {}", request.getTitle());

		User user = userService.getUserEntityByEmail(userEmail);

		PortfolioItem item = PortfolioItem.builder().title(request.getTitle()).description(request.getDescription())
				.content(request.getContent()).image(request.getImage()).technologies(request.getTechnologies())
				.features(request.getFeatures()).liveUrl(request.getLiveUrl()).githubUrl(request.getGithubUrl())
				.challenge(request.getChallenge()).solution(request.getSolution()).user(user).build();

		item = portfolioItemRepository.save(item);
		log.info("Portfolio item created successfully with id: {}", item.getId());

		return PortfolioItemDto.fromEntity(item);
	}

	@Transactional
	public PortfolioItemDto updatePortfolioItem(Long id, PortfolioItemRequest request, String userEmail) {
		log.info("Updating portfolio item with id: {}", id);

		PortfolioItem item = portfolioItemRepository.findById(id).orElseThrow(() -> {
			log.warn("Portfolio item not found with id: {}", id);
			return new ResourceNotFoundException("Portfolio item not found with id: " + id);
		});

		User user = userService.getUserEntityByEmail(userEmail);

		item.setTitle(request.getTitle());
		item.setDescription(request.getDescription());
		item.setContent(request.getContent());
		item.setImage(request.getImage());
		item.setTechnologies(request.getTechnologies());
		item.setFeatures(request.getFeatures());
		item.setLiveUrl(request.getLiveUrl());
		item.setGithubUrl(request.getGithubUrl());
		item.setChallenge(request.getChallenge());
		item.setSolution(request.getSolution());

		item = portfolioItemRepository.save(item);
		log.info("Portfolio item updated successfully: {}", item.getId());

		return PortfolioItemDto.fromEntity(item);
	}

	@Transactional
	public void deletePortfolioItem(Long id) {
		log.info("Deleting portfolio item with id: {}", id);

		if (!portfolioItemRepository.existsById(id)) {
			log.warn("Portfolio item not found with id: {}", id);
			throw new ResourceNotFoundException("Portfolio item not found with id: " + id);
		}

		portfolioItemRepository.deleteById(id);
		log.info("Portfolio item deleted successfully: {}", id);
	}
}
