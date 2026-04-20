package sn.smd.gestionbibliotheque.backend.service.email;

import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService {

    void sendEmail(String to, String subject, String text);

    void sendEmailWithAttachment(String to, String subject, String text, String filePath)
            throws MessagingException, IOException;
}