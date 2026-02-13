package com.tiv.spring.ai.doctor.service;

import com.tiv.spring.ai.doctor.enums.SSEMessageTypeEnum;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SSEService {

    SseEmitter connect(String userName);

    void disconnect(String userName);

    void sendMessage(String userName, String message, SSEMessageTypeEnum messageTypeEnum);

    void sendMessageAdd(String userName, String message);

    void sendMessageDone(String userName);

    void sendMessageAll(String message);

    int getOnlineCounts();

}
