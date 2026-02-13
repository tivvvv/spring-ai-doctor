package com.tiv.spring.ai.doctor.controller;

import com.tiv.spring.ai.doctor.enums.SSEMessageTypeEnum;
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

    @PostMapping(path = "/connect", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter connect(@RequestBody String userName) {
        return sseService.connect(userName);
    }

    @PostMapping(path = "/disconnect")
    public void disconnect(@RequestBody String userName) {
        sseService.disconnect(userName);
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody @Validated SSEMessage sseMessage) {
        sseService.sendMessage(sseMessage.getUserName(), sseMessage.getMessage(), SSEMessageTypeEnum.MESSAGE);
    }

    @PostMapping("/sendMessageAdd")
    public void sendMessageAdd(@RequestBody @Validated SSEMessage sseMessage) {
        sseService.sendMessageAdd(sseMessage.getUserName(), sseMessage.getMessage());
    }

    @PostMapping("/sendMessageAll")
    public void sendMessageAll(@RequestBody String message) {
        sseService.sendMessageAll(message);
    }

    @GetMapping("/onlineCounts")
    public Integer getOnlineCounts() {
        return sseService.getOnlineCounts();
    }

}
