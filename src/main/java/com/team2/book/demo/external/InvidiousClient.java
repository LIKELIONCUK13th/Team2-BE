package com.team2.book.demo.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class InvidiousClient {

    // Invidious 인스턴스 리스트
    private static final List<String> INSTANCES = List.of(
            "https://yewtu.be",
            "https://yewtu.eu",
            "https://yewtu.cool"
    );

    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<String> fetchRecommendedVideoUrls(String keyword) {
        for (String base : INSTANCES) {
            try {
                String url = base + "/api/v1/search?query="
                        + URLEncoder.encode(keyword, StandardCharsets.UTF_8);
                String json = rest.getForObject(url, String.class);

                // videoId 필드만 뽑아서 리스트로
                List<String> urls = StreamSupport.stream(
                                mapper.readTree(json).spliterator(), false)
                        .map(node -> node.path("videoId").asText())
                        .distinct()
                        .limit(3)
                        .map(id -> "https://youtu.be/" + id)
                        .collect(Collectors.toList());

                // 빈 리스트가 아니면 반환, 빈 리스트면 다음 인스턴스로
                if (!urls.isEmpty()) {
                    return urls;
                }
            } catch (HttpClientErrorException e) {
                // 429이면 다음 인스턴스, 그 외 에러도 일단 스킵
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    continue;
                }
            } catch (Exception e) {
                // 파싱오류 등 다른 예외도 건너뛰기
                continue;
            }
        }
        // 모든 인스턴스 실패 시 빈 리스트 리턴
        return Collections.emptyList();
    }
}
