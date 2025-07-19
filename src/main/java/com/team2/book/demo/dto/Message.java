package com.team2.book.demo.dto;

public class Message {
    private String role;     // 예: "user", "assistant"
    private String content;  // 사용자 입력 내용

    public Message() {}

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
