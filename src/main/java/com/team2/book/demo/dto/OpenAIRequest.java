package com.team2.book.demo.dto;

import java.util.List;

public class OpenAIRequest {
    private String model;
    private List<Message> messages;

    public OpenAIRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    public String getModel() {
        return this.model;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return this.role;
        }

        public String getContent() {
            return this.content;
        }
    }
}
