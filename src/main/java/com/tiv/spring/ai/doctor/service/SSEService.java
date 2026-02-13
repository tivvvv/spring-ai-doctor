package com.tiv.spring.ai.doctor.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SSEService {

    SseEmitter connect(String sessionId);

    void sendMessage(String sessionId, String message);

    void sendMessageAll(String message);

}
