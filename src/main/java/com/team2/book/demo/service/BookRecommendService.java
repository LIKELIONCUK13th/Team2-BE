package com.team2.book.demo.service;

import com.team2.book.demo.dto.OpenAIRequest;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookRecommendService {
    @Value("${openai.api_key}")
    private String openaiApiKey;
    @Value("${openai.api_url}")
    private String openaiApiUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private final BookSearchService bookSearchService;

    public BookRecommendService(BookSearchService bookSearchService) {
        this.bookSearchService = bookSearchService;
    }

    public String recommendBook(String emotion, String userId, String genre) {
        String prompt = String.format("\"%s\"라는 감정에 어울리는 책을 추천해줘. 책 제목만 알려줘.", emotion);
        OpenAIRequest openAIRequest = new OpenAIRequest("gpt-3.5-turbo", List.of(new OpenAIRequest.Message("user", prompt)));
        HttpEntity<OpenAIRequest> request = this.createHttpEntity(openAIRequest);
        Map<String, Object> response = (Map)this.restTemplate.postForObject(this.openaiApiUrl, request, Map.class, new Object[0]);
        String bookTitle = this.extractBookTitleFromResponse(response);
        return this.bookSearchService.searchBookInfoByTitle(bookTitle);
    }

    private String extractBookTitleFromResponse(Map<String, Object> response) {
        try {
            List<Map<String, Object>> choices = (List)response.get("choices");
            Map<String, Object> message = (Map)((Map)choices.get(0)).get("message");
            return (String)message.get("content");
        } catch (Exception var4) {
            return "추천 결과를 불러오지 못했습니다.";
        }
    }

    private HttpEntity<OpenAIRequest> createHttpEntity(OpenAIRequest openAIRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.openaiApiKey);
        headers.set("Content-Type", "application/json");
        return new HttpEntity(openAIRequest, headers);
    }
}
