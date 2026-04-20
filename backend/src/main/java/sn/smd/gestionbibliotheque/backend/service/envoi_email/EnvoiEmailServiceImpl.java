package sn.smd.gestionbibliotheque.backend.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromEmail;

    @Override
    public void sendEmail(String to, String subject, String text) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            message.setFrom(fromEmail);

            mailSender.send(message);

            log.info("Email envoyé à {}", to);

        } catch (MailException e) {
            log.error("Erreur envoi email : {}", e.getMessage());
            throw new RuntimeException("Erreur envoi email");
        }
    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String text, String filePath)
            throws MessagingException, IOException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        helper.setFrom(fromEmail);

        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IOException("Fichier introuvable");
        }

        byte[] fileBytes = Files.readAllBytes(path);

        helper.addAttachment(
                path.getFileName().toString(),
                new ByteArrayResource(fileBytes)
        );

        mailSender.send(message);

        log.info("Email avec pièce jointe envoyé à {}", to);
    }
}