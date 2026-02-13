package com.tiv.spring.ai.doctor.service;

import com.tiv.spring.ai.doctor.enums.SSEMessageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
@Service
public class SSEServiceImpl implements SSEService {

    private final Map<String, SseEmitter> sseClients = new ConcurrentHashMap<>(10 * 60);

    private final AtomicInteger onlineCounts = new AtomicInteger(0);

    @Override
    public SseEmitter connect(String sessionId) {
        SseEmitter sseEmitter = new SseEmitter();

        // 设置SSE连接的回调方法
        sseEmitter.onCompletion(completionCallback(sessionId));
        sseEmitter.onTimeout(timeoutCallback(sessionId));
        sseEmitter.onError(errorCallback(sessionId));

        sseClients.put(sessionId, sseEmitter);
        onlineCounts.getAndIncrement();
        log.info("创建SSE连接成功,会话id:{}", sessionId);
        return sseEmitter;
    }

    @Override
    public void disconnect(String sessionId) {
        if (CollectionUtils.isEmpty(sseClients) || !sseClients.containsKey(sessionId)) {
            return;
        }
        SseEmitter sseEmitter = sseClients.get(sessionId);
        sseEmitter.complete();
        log.info("断开SSE连接成功,会话id:{}", sessionId);
    }

    @Override
    public void sendMessage(String sessionId, String message, SSEMessageTypeEnum messageTypeEnum) {
        if (CollectionUtils.isEmpty(sseClients) || !sseClients.containsKey(sessionId)) {
            return;
        }
        SseEmitter sseEmitter = sseClients.get(sessionId);
        doSendMessage(sessionId, sseEmitter, messageTypeEnum, message);
    }

    @Override
    public void sendMessageAdd(String sessionId, String message) {
        sendMessage(sessionId, message, SSEMessageTypeEnum.ADD);
    }

    @Override
    public void sendMessageAll(String message) {
        if (CollectionUtils.isEmpty(sseClients)) {
            return;
        }
        sseClients.forEach((sessionId, sseEmitter) -> {
            doSendMessage(sessionId, sseEmitter, SSEMessageTypeEnum.MESSAGE, message);
        });
    }

    @Override
    public int getOnlineCounts() {
        return onlineCounts.intValue();
    }

    private void doSendMessage(String sessionId, SseEmitter sseEmitter, SSEMessageTypeEnum messageTypeEnum, String message) {
        SseEmitter.SseEventBuilder msg = SseEmitter.event()
                .id(sessionId)
                .name(messageTypeEnum.getType())
                .data(message);
        try {
            sseEmitter.send(msg);
        } catch (IOException e) {
            log.error("SSE发送消息失败,会话id:{}", sessionId);
            removeClient(sessionId);
            throw new RuntimeException(e);
        }
    }

    private Runnable completionCallback(String sessionId) {
        return () -> {
            log.info("SSE连接已关闭,会话id:{}", sessionId);
            removeClient(sessionId);
        };
    }

    private Runnable timeoutCallback(String sessionId) {
        return () -> {
            log.info("SSE连接已超时,会话id:{}", sessionId);
            removeClient(sessionId);
        };
    }

    private Consumer<Throwable> errorCallback(String sessionId) {
        return Throwable -> {
            log.info("SSE连接发生错误,会话id:{}", sessionId);
            removeClient(sessionId);
        };
    }

    private void removeClient(String sessionId) {
        sseClients.remove(sessionId);
        onlineCounts.getAndDecrement();
        log.info("SSE连接移除成功,会话id:{}", sessionId);
    }

}
