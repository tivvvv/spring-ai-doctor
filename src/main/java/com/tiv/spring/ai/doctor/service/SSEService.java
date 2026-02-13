package com.tiv.spring.ai.doctor.service;

import com.tiv.spring.ai.doctor.enums.SSEMessageTypeEnum;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SSEService {

    SseEmitter connect(String sessionId);

    void disconnect(String sessionId);

    void sendMessage(String sessionId, String message, SSEMessageTypeEnum messageTypeEnum);

    void sendMessageAdd(String sessionId, String message);

    void sendMessageAll(String message);

}
