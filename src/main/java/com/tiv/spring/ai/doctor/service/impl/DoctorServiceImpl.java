package com.tiv.spring.ai.doctor.service.impl;

import com.tiv.spring.ai.doctor.model.SSEMessage;
import com.tiv.spring.ai.doctor.service.DoctorService;
import com.tiv.spring.ai.doctor.service.SSEService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;

@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {

    @Resource
    private OllamaChatClient ollamaChatClient;

    @Resource
    private SSEService sseService;

    @Override
    public void chat(SSEMessage sseMessage) {
        Prompt prompt = new Prompt(new UserMessage(sseMessage.getMessage()));
        Flux<ChatResponse> responseFlux = ollamaChatClient.stream(prompt);
        String result = responseFlux.toStream().map(response -> {
            String content = response.getResult().getOutput().getContent();
            sseService.sendMessageAdd(sseMessage.getUserName(), content);
            return content;
        }).collect(Collectors.joining());
        sseService.sendMessageDone(sseMessage.getUserName());
    }

}
