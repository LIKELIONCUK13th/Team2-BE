package com.team2.book.demo.service;

import com.team2.book.demo.dto.AnswerRequest;
import com.team2.book.demo.dto.CounselingResult;
import com.team2.book.demo.external.InvidiousClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounselingService {

    private final InvidiousClient invidious;

    public CounselingService(InvidiousClient invidious) {
        this.invidious = invidious;
    }

    public String createSession(List<AnswerRequest> answers) {
        return java.util.UUID.randomUUID().toString();
    }

    public CounselingResult getResult(String sessionId) {
        // 점수 계산로직 등...
        return new CounselingResult(
                "당신은 전반적으로 스트레스 지수가 높습니다.",
                "가벼운 산책과 명상을 추천드립니다!"
        );
    }

    public List<String> getRecommendations(String sessionId) {
        CounselingResult result = getResult(sessionId);
        String keyword = extractKeywordFrom(result.getSummary());

        // 실제 YouTube 링크 3개 가져오기
        return invidious.fetchRecommendedVideoUrls(keyword);
    }

    private String extractKeywordFrom(String summary) {
        return summary.replaceAll(".*(스트레스).*", "$1");
    }
}
