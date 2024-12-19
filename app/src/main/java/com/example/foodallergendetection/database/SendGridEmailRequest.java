package com.example.foodallergendetection.database;

public class SendGridEmailRequest {

    private Personalization[] personalizations;
    private From from;
    private Content[] content;

    public SendGridEmailRequest(Personalization[] personalizations, From from, Content[] content) {
        this.personalizations = personalizations;
        this.from = from;
        this.content = content;
    }

    // Getters and Setters

    public static class Personalization {
        private String[] to;
        private String subject;

        public Personalization(String[] to, String subject) {
            this.to = to;
            this.subject = subject;
        }

        // Getters and Setters
    }

    public static class From {
        private String email;

        public From(String email) {
            this.email = email;
        }

        // Getter and Setter
    }

    public static class Content {
        private String type;
        private String value;

        public Content(String type, String value) {
            this.type = type;
            this.value = value;
        }

        // Getter and Setter
    }
}
