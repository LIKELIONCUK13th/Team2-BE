package com.team2.book.demo.controller;

import com.team2.book.demo.dto.AnswerRequest;
import com.team2.book.demo.dto.CounselingResult;
import com.team2.book.demo.service.CounselingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
@CrossOrigin(origins="http://localhost:5173")
@RequiredArgsConstructor
public class CounselingController {

    private final CounselingService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String,String> createSession(@RequestBody List<AnswerRequest> answers){
        String id = service.createSession(answers);
        return Collections.singletonMap("sessionId",id);
    }

    @GetMapping("/{sessionId}/result")
    public CounselingResult getResult(@PathVariable String sessionId){
        return service.getResult(sessionId);
    }

    @GetMapping("/{sessionId}/recommendations")
    public List<String> getRecommendations(@PathVariable String sessionId){
        return service.getRecommendations(sessionId);
    }

}
