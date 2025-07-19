package com.team2.book.demo.service;

import com.team2.book.demo.dto.*;
import com.team2.book.demo.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private final String API_KEY;

    // 생성자에서 주입받기
    public BoardService(@Value("${openai.api.key}") String apiKey) {
        this.API_KEY = apiKey;
        System.out.println("API_KEY: " + API_KEY);
    }



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
당신은 GPT 프롬프트 디자이너입니다.
사용자가 입력한 질문과 조건을 바탕으로, GPT가 보다 정확하고 풍부한 답변을 생성할 수 있도록 고도화된 프롬프트를 작성해 주세요.
출력은 반드시 아래 두 항목으로 구성해 주세요. 단, 해당 형식은 한 번만 출력해 주세요. 반복해서 [System], [User] 항목을 다시 작성하지 마세요.

[System 메시지]
GPT가 자신에게 주어진 역할을 명확히 인식할 수 있도록, 충분한 맥락과 설명을 포함해 작성해 주세요.
단순히 직업만 제시하는 것이 아니라, 그 역할에서 기대되는 말투, 태도, 정보 제공 방식까지 구체적으로 서술해 주세요.
System 메시지는 반드시 ‘당신은’으로 시작해야 합니다.
모든 System 메시지와 User 메시지는 반드시 존댓말로 작성해 주세요. 반말은 절대 사용하지 마세요.

예시:
"당신은 친절하고 인내심 많은 심리상담가입니다. 내담자가 감정을 솔직히 표현할 수 있도록, 부드럽고 지지적인 말투로 조언을 제공해야 합니다."

[User 메시지]
사용자의 원래 질문을 아래 조건(목적, 스타일, 배경 정보)에 맞게 보완하여 다시 구성해 주세요.
질문은 보다 구체적이고 명확해야 하며, 상황적 맥락과 기대하는 답변 스타일이 자연스럽게 녹아 있어야 합니다.
문장은 반드시 자연스럽고 예의 있는 존댓말 형태로 작성해 주세요. 필요하다면 간단한 배경 설명을 앞에 덧붙여도 괜찮습니다.
사용자가 제공한 조건은 다음과 같습니다:
GPT 사용 목적: %s
원하는 답변 스타일: %s
배경 정보: %s
출력은 아래 형식을 따라 주세요. 이 형식은 딱 한 번만 사용해 주세요.

[System] <GPT의 역할과 응답 방식에 대한 풍부한 설정>
[User] <질문을 보완하여 다시 구성한 형태>

※ 생성된 프롬프트에는 반드시 GPT의 역할이 명확하게 드러나야 하며, 질문은 목적과 스타일에 부합하도록 정교하게 보완되어야 합니다.
또한, System 메시지와 User 메시지 모두 반드시 존댓말로 작성되어야 하며, 반말이나 비격식 표현은 사용하지 말아 주세요.
응답은 단순하거나 모호하지 않도록 충분한 정보와 의도를 담아 고품질로 작성해 주세요.                                                                                                                               내용이 지나치게 단순하거나 일반적이지 않도록 주의해 주시고, 충분한 정보와 의도가 반영된 고품질의 응답을 생성해 주세요.
""".formatted(purpose, String.join(", ", style), backgroundInfo)),

                new Message("user", "다음 질문을 분석해서 프롬프트를 구성해줘: " + question)
        );

        ChatRequest chatRequest = new ChatRequest("gpt-3.5-turbo", messages);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

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
        headers.setBearerAuth(API_KEY);

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
