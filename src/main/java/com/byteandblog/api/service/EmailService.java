package com.byteandblog.api.service;


import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Logger logger = Logger.getLogger(EmailService.class.getName());

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${application.name}")
    private String applicationName;
    
    @Value("${${spring.mail.username}")
    private String contactEmail;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        logger.info("EmailService initialized with JavaMailSender and TemplateEngine");
    }

    @Async
    public void sendPasswordResetEmail(String to, String name, String resetLink) {
        logger.info("Sending password reset email to: " + to);
        
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("resetLink", resetLink);
            context.setVariable("applicationName", applicationName);
            
            String htmlContent = templateEngine.process("password-reset", context);
            
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Password Reset Request");
            helper.setText(htmlContent, true);
            
            mailSender.send(message);
            logger.info("Password reset email sent successfully to: " + to);
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Failed to send password reset email to: " + to, e);
        }
    }
    
    /**
     * Sends a contact form message to the admin email
     * 
     * @param senderName Name of the person sending the message
     * @param senderEmail Email of the person sending the message
     * @param subject Subject of the message
     * @param messageContent Content of the message
     * @return true if the email was sent successfully, false otherwise
     */
    @Async
    public CompletableFuture<Boolean> sendContactFormEmail(String senderName, String senderEmail, String subject, String messageContent) {
        logger.info("Sending contact form email from: " + senderEmail);
        
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            // Create HTML content for the email
            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<html><body>");
            htmlBuilder.append("<h2>New Contact Form Submission</h2>");
            htmlBuilder.append("<p><strong>From:</strong> ").append(senderName).append(" (").append(senderEmail).append(")</p>");
            htmlBuilder.append("<p><strong>Subject:</strong> ").append(subject).append("</p>");
            htmlBuilder.append("<h3>Message:</h3>");
            htmlBuilder.append("<p>").append(messageContent.replace("\n", "<br/>")).append("</p>");
            htmlBuilder.append("<hr/>");
            htmlBuilder.append("<p>This email was sent from the contact form on ").append(applicationName).append("</p>");
            htmlBuilder.append("</body></html>");
            
            helper.setFrom(fromEmail);
            helper.setTo(contactEmail);
            helper.setReplyTo(senderEmail);
            helper.setSubject("Contact Form: " + subject);
            helper.setText(htmlBuilder.toString(), true);
            
            mailSender.send(message);
            logger.info("Contact form email sent successfully from: " + senderEmail);
            return CompletableFuture.completedFuture(true);
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Failed to send contact form email from: " + senderEmail, e);
            return CompletableFuture.completedFuture(false);
        }
    }
}
