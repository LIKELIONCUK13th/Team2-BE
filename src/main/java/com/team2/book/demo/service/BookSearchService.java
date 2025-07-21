package com.team2.book.demo.service;

import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

@Service
public class BookSearchService {
    private final RestTemplate restTemplate = new RestTemplate();

    public BookSearchService() {
    }

    public String searchBookInfoByTitle(String bookTitle) {
        String apiKey = "YOUR_KEY";
        String baseUrl = "https://www.nl.go.kr/seoji/SearchApi.do";
        String url = baseUrl + "?cert_key=" + apiKey + "&result_style=json&page_no=1&page_size=1&title=" + UriUtils.encode(bookTitle, StandardCharsets.UTF_8);

        try {
            return (String)this.restTemplate.getForObject(url, String.class, new Object[0]);
        } catch (Exception var6) {
            return "책 정보를 불러오지 못했습니다.";
        }
    }
}
