package com.team2.book.demo.controller;

import com.team2.book.demo.dto.RequestDto;
import com.team2.book.demo.dto.ResponseDto;
import com.team2.book.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// chatgpt api
import org.springframework.http.ResponseEntity;


@RestController
@CrossOrigin(origins = "http://192.168.0.71:5173")
@RequestMapping("/api/prompt/posts")
public class to_Controller {

    @Autowired
    private BoardService boardService;

    // 0. 테스트용 전체 조회
    //@GetMapping("/readall")
    //public List<RequestDto> getBoard() {
    //    return boardService.readall();
    //}
    

    //chatgpt api
    private final BoardService toService;

    public to_Controller(BoardService toService) {
        this.toService = toService;
    }

    //저장 기능은 존재하지 않음
    @PostMapping
    public ResponseEntity<ResponseDto> ask(@RequestBody RequestDto requestDto) {
        String answer = toService.askChatGPT(requestDto);
        return ResponseEntity.ok(new ResponseDto(answer));
    }
}
