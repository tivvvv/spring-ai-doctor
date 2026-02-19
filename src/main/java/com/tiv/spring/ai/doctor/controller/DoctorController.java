package com.tiv.spring.ai.doctor.controller;

import com.tiv.spring.ai.doctor.model.ChatRecord;
import com.tiv.spring.ai.doctor.model.SSEMessage;
import com.tiv.spring.ai.doctor.service.ChatRecordService;
import com.tiv.spring.ai.doctor.service.DoctorService;
import com.tiv.spring.ai.doctor.service.SSEService;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

/**
 * 医生控制器
 */
@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Resource
    private SSEService sseService;

    @Resource
    private DoctorService doctorService;

    @Resource
    private ChatRecordService chatRecordService;

    /**
     * 创建和医生的SSE连接
     *
     * @param userName
     * @return
     */
    @PostMapping(path = "/connect", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter connect(@RequestBody String userName) {
        return sseService.connect(userName);
    }

    /**
     * 和医生对话
     *
     * @param sseMessage
     */
    @PostMapping("/chat")
    public void chat(@RequestBody SSEMessage sseMessage) {
        doctorService.chat(sseMessage);
    }

    /**
     * 获取用户和医生的聊天记录
     *
     * @param userName
     * @return
     */
    @GetMapping("/records")
    public List<ChatRecord> getRecords(@RequestBody String userName) {
        return chatRecordService.getRecords(userName);
    }

    /**
     * 获取当前在线用户总数
     *
     * @return
     */
    @GetMapping("/onlineCounts")
    public Integer getOnlineCounts() {
        return sseService.getOnlineCounts();
    }

}