package com.example.foodallergendetection.database;
import android.util.Log;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendGridHelper {

    public static void sendOtpEmail(String recipientEmail, String otp) {
        Log.d("fad", "starting email  -->: ");
        // SMTP Server configuration
        final String smtpHost = "smtp.sendgrid.net";
        final String smtpPort = "587";
        final String username = "apikey"; // SendGrid requires 'apikey' as the username
        final String password = "mlsn.ea78d67781d65f5e2d51893afb832995cbe0ecf92db74716d64f3ff972fee1b8"; // Replace with your actual API Key

        // Email content
        String subject = "OTP Verification";
        String body = "Your OTP is: " + otp;

        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        // Authenticate and create the session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Compose the email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("farzeenshahzad18@gmail.com")); // Replace with your verified email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            Log.d("fad", "message sent  -->: ");
            System.out.println("OTP email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            Log.d("fad", "message not sent  -->: ");
            System.err.println("Error while sending email: " + e.getMessage());
        }
    }
}
