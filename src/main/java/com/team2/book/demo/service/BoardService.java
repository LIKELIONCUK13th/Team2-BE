package com.team2.book.demo.service;

import com.team2.book.demo.dto.*;
import com.team2.book.demo.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@Transactional
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    private final String API_KEY = "Bearer 키 임시로 지움"; // 실제 키로 교체
    private final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final String WIKI_API_URL = "https://ko.wikipedia.org/w/api.php";

    private final RestTemplate restTemplate = new RestTemplate();

    public String askChatGPT(RequestDto requestDto) {
        String question = requestDto.getQuestion();
        List<String> style = requestDto.getStyle();
        String purpose = requestDto.getPurpose();

        // 1단계: 핵심 키워드 추출
        String keyword = extractKeyword(question);

        // 2단계: Wikipedia 요약 가져오기
        String wikiSummary = getWikipediaSummary(keyword);
        String backgroundInfo = (wikiSummary != null && !wikiSummary.isBlank())
                ? wikiSummary
                : "관련 배경 정보를 찾을 수 없습니다.";

        // 3단계: ChatGPT에 보정 요청
        List<Message> messages = List.of(
                new Message("system", """
너는 GPT 프롬프트 디자이너야.
사용자가 입력한 질문과 조건을 바탕으로, GPT가 더 좋은 답변을 할 수 있도록 프롬프트를 구성해줘.
다음 2가지로 구성된 결과를 출력해:

[System 메시지]
- GPT가 역할을 명확히 인식할 수 있도록 설정해줘. 예: "당신은 친절한 심리상담가입니다", "당신은 냉철한 수학 선생님입니다" 등

[User 메시지]
- 사용자의 원래의 질문을 목적과 스타일, 배경정보에 맞게 보정해서, 더 구체적이고 명확하게 답을 얻을 수 있도록 질문을 다시 써줘.

조건:
- 목적: %s
- 스타일: %s
- 배경정보: %s

결과는 아래 형식을 따라줘:

System: <내용>
User: <내용>
""".formatted(purpose, String.join(", ", style), backgroundInfo)),

                new Message("user", "다음 질문을 분석해서 프롬프트를 구성해줘: " + question)
        );

        ChatRequest chatRequest = new ChatRequest("gpt-3.5-turbo", messages);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", API_KEY);

        HttpEntity<ChatRequest> httpRequest = new HttpEntity<>(chatRequest, headers);

        ResponseEntity<ChatResponse> response = restTemplate.exchange(
                API_URL, HttpMethod.POST, httpRequest, ChatResponse.class
        );

        return response.getBody().getChoices().get(0).getMessage().getContent();
    }

    // 🔍 핵심 키워드 추출
    private String extractKeyword(String question) {
        List<Message> messages = List.of(
                new Message("system", "당신은 텍스트 분석 전문가입니다."),
                new Message("user", """
다음 문장에서 가장 중요한 핵심 개념 하나만 '명사'로 짧게 뽑아줘. 부연 설명 없이 단어 하나만 출력해.

문장: %s
""".formatted(question))
        );

        ChatRequest keywordRequest = new ChatRequest("gpt-3.5-turbo", messages);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", API_KEY);

        HttpEntity<ChatRequest> request = new HttpEntity<>(keywordRequest, headers);
        ResponseEntity<ChatResponse> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, ChatResponse.class);

        return response.getBody().getChoices().get(0).getMessage().getContent().strip();
    }

    // 📚 Wikipedia 요약
    private String getWikipediaSummary(String keyword) {
        String url = UriComponentsBuilder.fromHttpUrl(WIKI_API_URL)
                .queryParam("action", "query")
                .queryParam("format", "json")
                .queryParam("titles", keyword)
                .queryParam("prop", "extracts")
                .queryParam("exintro", true)
                .queryParam("explaintext", true)
                .toUriString();

        try {
            Map response = restTemplate.getForObject(url, Map.class);
            Map pages = (Map) ((Map) response.get("query")).get("pages");

            for (Object pageObj : pages.values()) {
                Map page = (Map) pageObj;
                Object extract = page.get("extract");
                return extract != null ? extract.toString().strip() : null;
            }
        } catch (Exception e) {
            System.out.println("Wikipedia 요약 실패: " + e.getMessage());
        }

        return null;
    }
}
