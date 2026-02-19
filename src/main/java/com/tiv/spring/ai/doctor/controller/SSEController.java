package com.tiv.spring.ai.doctor.controller;

import com.tiv.spring.ai.doctor.enums.SSEMessageTypeEnum;
import com.tiv.spring.ai.doctor.model.SSEMessage;
import com.tiv.spring.ai.doctor.service.SSEService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台SSE控制器
 */
@RestController
@RequestMapping("/sse")
public class SSEController {

    @Resource
    private SSEService sseService;

    /**
     * 断开指定用户的SSE连接
     *
     * @param userName
     */
    @PostMapping(path = "/disconnect")
    public void disconnect(@RequestBody String userName) {
        sseService.disconnect(userName);
    }

    /**
     * 给指定用户推送普通消息
     *
     * @param sseMessage
     */
    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody @Validated SSEMessage sseMessage) {
        sseService.sendMessage(sseMessage.getUserName(), sseMessage.getMessage(), SSEMessageTypeEnum.MESSAGE);
    }

    /**
     * 给指定用户推送追加消息
     *
     * @param sseMessage
     */
    @PostMapping("/sendMessageAdd")
    public void sendMessageAdd(@RequestBody @Validated SSEMessage sseMessage) {
        sseService.sendMessageAdd(sseMessage.getUserName(), sseMessage.getMessage());
    }

    /**
     * 给指定用户推送消息完成
     *
     * @param userName
     */
    @PostMapping("/sendMessageDone")
    public void sendMessageDone(@RequestBody String userName) {
        sseService.sendMessageDone(userName);
    }

    /**
     * 给所有用户推送普通消息
     *
     * @param message
     */
    @PostMapping("/sendMessageAll")
    public void sendMessageAll(@RequestBody String message) {
        sseService.sendMessageAll(message);
    }

}
