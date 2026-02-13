package com.tiv.spring.ai.doctor.controller;

import com.tiv.spring.ai.doctor.model.SSEMessage;
import com.tiv.spring.ai.doctor.service.SSEService;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
public class SSEController {

    @Resource
    private SSEService sseService;

    @GetMapping(path = "/connect", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter connect(@RequestParam String sessionId) {
        return sseService.connect(sessionId);
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody @Validated SSEMessage sseMessage) {
        sseService.sendMessage(sseMessage.getSessionId(), sseMessage.getMessage());
    }

}
