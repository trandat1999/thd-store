package com.thd.store.service.impl;

import com.thd.store.service.MailService;
import com.thd.store.entity.EmailNotification;
import com.thd.store.entity.User;
import com.thd.store.entity.VerificationToken;
import com.thd.store.exception.StoreException;
import com.thd.store.repository.EmailNotificationRepository;
import com.thd.store.repository.VerificationTokenRepository;
import com.thd.store.type.TypeEmail;
import com.thd.store.util.ConstUtil;
import com.thd.store.util.SystemMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author DatNuclear 19/01/2024
 * @project store-movie
 */
@Service
@AllArgsConstructor
@Slf4j
public class MailServiceImpl extends BaseService implements MailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailNotificationRepository emailNotificationRepository;
    private final PasswordEncoder passwordEncoder;

    @Async
    public void sendMail(EmailNotification notificationEmail, Map<String, Object> data) {
        String mailTemplate = ConstUtil.MAIL_TEMPLATE_ACTIVE_USER;
        HashMap<String, Object> attributes = new HashMap<>();
        if(!CollectionUtils.isEmpty(data)){
            attributes.putAll(data);
        }
        attributes.put(ConstUtil.PARAM_ACTIVE_ACCOUNT, notificationEmail.getLink());
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom("noreply@example.com");
            messageHelper.setTo(notificationEmail.getEmail());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(buildContent(attributes, mailTemplate), true);
        };
        try {
            mailSender.send(mimeMessagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail because of exception {}", e.getMessage());
            throw new StoreException(getMessage(SystemMessage.SEND_MAIL_ERROR, notificationEmail.getEmail()), e);
        }
    }

    @Override
    @Async
    public void sendMailActive(User user) {
        VerificationToken verificationToken = generateVerificationToken(user);
        EmailNotification emailNotification = EmailNotification.builder()
                .email(user.getEmail())
                .type(TypeEmail.REGISTER)
                .content(SystemMessage.CONTENT_MAIL_REGISTER)
                .subject(SystemMessage.SUBJECT_MAIL_REGISTER)
                .user(user)
                .link(ConstUtil.CLIENT_URL + ConstUtil.SLASH + ConstUtil.CLIENT_URL_VERIFICATION_ACCOUNT
                        + ConstUtil.SLASH + verificationToken.getToken())
                .build();
        emailNotification = emailNotificationRepository.save(emailNotification);
        sendMail(emailNotification,null);
    }

    @Async
    @Override
    public void sendMailForgotPassword(User user) {
        VerificationToken verificationToken = generateVerificationToken(user);
        String newPassword = generatePassword();
        verificationToken.setPassword(passwordEncoder.encode(newPassword));
        verificationTokenRepository.save(verificationToken);
        EmailNotification emailNotification = EmailNotification.builder()
                .email(user.getEmail())
                .type(TypeEmail.REGISTER)
                .content(SystemMessage.CONTENT_MAIL_FORGOT)
                .subject(SystemMessage.SUBJECT_MAIL_FORGOT)
                .user(user)
                .link(ConstUtil.CLIENT_URL + ConstUtil.SLASH + ConstUtil.CLIENT_URL_FORGOT_PASSWORD
                        + ConstUtil.SLASH + verificationToken.getToken())
                .build();
        emailNotification = emailNotificationRepository.save(emailNotification);
        sendMail(emailNotification, Collections.singletonMap(ConstUtil.PARAM_NEW_PASSWORD,newPassword));
    }

    private VerificationToken generateVerificationToken(User user) {
        VerificationToken verificationToken = VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryTime(LocalDateTime.now().plusMinutes(ConstUtil.TIME_MINUTES_EXPIRED_VERIFICATION_TOKEN))
                .build();
        verificationToken = verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }
    private String buildContent(String content) {
        Context context = new Context();
        context.setVariable("currentYear", LocalDateTime.now().getYear());
        context.setVariable("confirm", content);
        context.setVariable("host", ConstUtil.HOST_URL);
        return templateEngine.process("mail/mailTemplate", context);
    }
    private String buildContent(Map<String, Object> content, String staticTemplate) {
        Context context = new Context();
        if (!CollectionUtils.isEmpty(content)) {
            content.forEach((String key, Object value) -> {
                context.setVariable(key, value);
            });
        }
        return templateEngine.process(staticTemplate, context);
    }
    private String generatePassword(){
        Random rand = new Random();
        return String.valueOf(rand.nextInt(100000,999999));
    }
}
