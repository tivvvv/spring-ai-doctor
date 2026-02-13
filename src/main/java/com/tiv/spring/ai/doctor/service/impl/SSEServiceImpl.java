package com.tiv.spring.ai.doctor.service.impl;

import com.tiv.spring.ai.doctor.enums.SSEMessageTypeEnum;
import com.tiv.spring.ai.doctor.service.SSEService;
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
    public SseEmitter connect(String userName) {
        SseEmitter sseEmitter = new SseEmitter();

        // 设置SSE连接的回调方法
        sseEmitter.onCompletion(completionCallback(userName));
        sseEmitter.onTimeout(timeoutCallback(userName));
        sseEmitter.onError(errorCallback(userName));

        sseClients.put(userName, sseEmitter);
        onlineCounts.getAndIncrement();
        log.info("创建SSE连接成功,用户名:{}", userName);
        return sseEmitter;
    }

    @Override
    public void disconnect(String userName) {
        if (CollectionUtils.isEmpty(sseClients) || !sseClients.containsKey(userName)) {
            return;
        }
        SseEmitter sseEmitter = sseClients.get(userName);
        sseEmitter.complete();
        log.info("断开SSE连接成功,用户名:{}", userName);
    }

    @Override
    public void sendMessage(String userName, String message, SSEMessageTypeEnum messageTypeEnum) {
        if (CollectionUtils.isEmpty(sseClients) || !sseClients.containsKey(userName)) {
            return;
        }
        SseEmitter sseEmitter = sseClients.get(userName);
        doSendMessage(userName, sseEmitter, messageTypeEnum, message);
    }

    @Override
    public void sendMessageAdd(String userName, String message) {
        sendMessage(userName, message, SSEMessageTypeEnum.ADD);
    }

    @Override
    public void sendMessageDone(String userName) {
        sendMessage(userName, SSEMessageTypeEnum.DONE.getDesc(), SSEMessageTypeEnum.DONE);
    }

    @Override
    public void sendMessageAll(String message) {
        if (CollectionUtils.isEmpty(sseClients)) {
            return;
        }
        sseClients.forEach((userName, sseEmitter) -> {
            doSendMessage(userName, sseEmitter, SSEMessageTypeEnum.MESSAGE, message);
        });
    }

    @Override
    public int getOnlineCounts() {
        return onlineCounts.intValue();
    }

    private void doSendMessage(String userName, SseEmitter sseEmitter, SSEMessageTypeEnum messageTypeEnum, String message) {
        SseEmitter.SseEventBuilder msg = SseEmitter.event()
                .id(userName)
                .name(messageTypeEnum.getType())
                .data(message);
        try {
            sseEmitter.send(msg);
        } catch (IOException e) {
            log.error("SSE发送消息失败,用户名:{}", userName);
            removeClient(userName);
            throw new RuntimeException(e);
        }
    }

    private Runnable completionCallback(String userName) {
        return () -> {
            log.info("SSE连接已关闭,用户名:{}", userName);
            removeClient(userName);
        };
    }

    private Runnable timeoutCallback(String userName) {
        return () -> {
            log.info("SSE连接已超时,用户名:{}", userName);
            removeClient(userName);
        };
    }

    private Consumer<Throwable> errorCallback(String userName) {
        return Throwable -> {
            log.info("SSE连接发生错误,用户名:{}", userName);
            removeClient(userName);
        };
    }

    private void removeClient(String userName) {
        sseClients.remove(userName);
        onlineCounts.getAndDecrement();
        log.info("SSE连接移除成功,用户名:{}", userName);
    }

}
