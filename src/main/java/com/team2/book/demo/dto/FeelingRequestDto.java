package com.team2.book.demo.dto;

public class FeelingRequestDto {
    private String userId;
    private String emotion;
    private String preferredGenre;

    public FeelingRequestDto() {
    }

    public FeelingRequestDto(String userId, String emotion, String preferredGenre) {
        this.userId = userId;
        this.emotion = emotion;
        this.preferredGenre = preferredGenre;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmotion() {
        return this.emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getPreferredGenre() {
        return this.preferredGenre;
    }

    public void setPreferredGenre(String preferredGenre) {
        this.preferredGenre = preferredGenre;
    }

    public String toString() {
        return "FeelingRequestDto{userId=" + this.userId + ", emotion='" + this.emotion + "', preferredGenre='" + this.preferredGenre + "'}";
    }
}
