
package com.team2.book.demo.controller;

import com.team2.book.demo.dto.FeelingRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookRecommendController {

    @PostMapping("/recommend-book")
    public ResponseEntity<String> recommendBook(@RequestBody FeelingRequestDto requestDto) {
        System.out.println("사용자 ID: " + requestDto.getUserId());
        System.out.println("감정: " + requestDto.getEmotion());
        System.out.println("선호 장르: " + requestDto.getPreferredGenre());

        return ResponseEntity.ok("추천 요청 완료 (임시 응답)");
    }
}
