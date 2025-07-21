package com.team2.book.demo.controller;

import com.team2.book.demo.dto.FeelingRequestDto;
import com.team2.book.demo.service.BookRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api"})
public class BookRecommendController {
    @Autowired
    private final BookRecommendService bookRecommendService;

    public BookRecommendController(BookRecommendService bookRecommendService) {
        this.bookRecommendService = bookRecommendService;
    }

    @PostMapping({"/recommend-book"})
    public ResponseEntity<String> recommendBook(@RequestBody FeelingRequestDto requestDto) {
        System.out.println("사용자 ID: " + requestDto.getUserId());
        System.out.println("감정: " + requestDto.getEmotion());
        System.out.println("선호 장르: " + requestDto.getPreferredGenre());
        String result = this.bookRecommendService.recommendBook(requestDto.getEmotion(), requestDto.getUserId(), requestDto.getPreferredGenre());
        return ResponseEntity.ok(result);
    }
}
