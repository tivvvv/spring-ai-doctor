package com.tiv.spring.ai.doctor.controller;

import com.tiv.spring.ai.doctor.model.SSEMessage;
import com.tiv.spring.ai.doctor.service.SSEService;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
public class SSEController {

    @Resource
    private SSEService sseService;

    @PostMapping(path = "/connect", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter connect(@RequestBody String sessionId) {
        return sseService.connect(sessionId);
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody @Validated SSEMessage sseMessage) {
        sseService.sendMessage(sseMessage.getSessionId(), sseMessage.getMessage());
    }

    @PostMapping("/sendMessageAll")
    public void sendMessageAll(@RequestBody String message) {
        sseService.sendMessageAll(message);
    }

}
