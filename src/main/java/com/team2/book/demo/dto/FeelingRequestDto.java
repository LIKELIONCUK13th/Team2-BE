
package com.team2.book.demo.dto;

public class FeelingRequestDto {

    private Long userId;           // 사용자 ID
    private String emotion;        // 감정: 예) "슬픔", "기쁨"
    private String preferredGenre; // 예) "로맨스", "추리", "판타지"

    public FeelingRequestDto() {
    }

    public FeelingRequestDto(Long userId, String emotion, String preferredGenre) {
        this.userId = userId;
        this.emotion = emotion;
        this.preferredGenre = preferredGenre;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getPreferredGenre() {
        return preferredGenre;
    }

    public void setPreferredGenre(String preferredGenre) {
        this.preferredGenre = preferredGenre;
    }

    @Override
    public String toString() {
        return "FeelingRequestDto{" +
                "userId=" + userId +
                ", emotion='" + emotion + '\'' +
                ", preferredGenre='" + preferredGenre + '\'' +
                '}';
    }
}
