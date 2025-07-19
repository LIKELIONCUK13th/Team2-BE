package com.team2.book.demo.dto;

// 데이터 전송의 역할, 비지니스 로직 없음,

import java.util.List;

public class RequestDto {
    private Integer id;
    private String purpose;
    // 수정 필요
    private List<String> style;
    private String question;

    public RequestDto(String purpose, List<String> style, String question) {
    this.purpose = purpose;
    this.style = style;
    this.question = question;
    }

    public RequestDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public List<String> getStyle() {
        return style;
    }

    public void setStyle(List<String> style) {
        this.style = style;
    }

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }
}