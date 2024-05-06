package com.skillseekr.API;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailAPI {
    private static final String username = "hjness66@gmail.com"; // Votre adresse Gmail
    private static final String password = "cedz bgkg qgkl iudo"; // Votre mot de passe Gmail
    private static Session session; // Session SMTP

    static {
        // Propriétés pour la configuration SMTP de Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Création d'une session d'authentification
        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public static void sendEmail(String recipientEmail, String subject, String messageBody) {
        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            // Envoi du message
            Transport.send(message);

            System.out.println("E-mail sent successfully.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendEmailVerification(String recipientEmail) {
        try {
            // Création du message de vérification de l'e-mail
            Message verificationMessage = new MimeMessage(session);
            verificationMessage.setFrom(new InternetAddress(username));
            verificationMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            verificationMessage.setSubject("Email Verification");
            verificationMessage.setText("Welcome to Skillseekr! Thank you for registering with us.\n\n" +
                    "Your account is awaiting administrative approval. You will receive an email notification once it is activated.");


            // Envoi du message de vérification
            Transport.send(verificationMessage);

            System.out.println("Email sent successfully for verification.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendEmailWithOTP(String recipientEmail, String otp) {
        try {
            // Création du message avec le code OTP pour vérification
            Message otpMessage = new MimeMessage(session);
            otpMessage.setFrom(new InternetAddress(username));
            otpMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            otpMessage.setSubject("Code OTP pour vérification");

            // Ajout du code OTP au contenu du message
            otpMessage.setText("Votre code OTP est : " + otp);

            // Envoi du message avec le code OTP
            Transport.send(otpMessage);

            System.out.println("E-mail sent successfully with OTP code for verification.");
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }

    // Méthode pour générer un code aléatoire
    public static String generateRandomCode(int length) {
        String chars = "0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            code.append(chars.charAt(index));
        }
        return code.toString();
    }
}
