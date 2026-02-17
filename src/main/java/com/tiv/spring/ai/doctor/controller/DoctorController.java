package com.tiv.spring.ai.doctor.controller;

import com.tiv.spring.ai.doctor.model.ChatRecord;
import com.tiv.spring.ai.doctor.model.SSEMessage;
import com.tiv.spring.ai.doctor.service.ChatRecordService;
import com.tiv.spring.ai.doctor.service.DoctorService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Resource
    private DoctorService doctorService;

    @Resource
    private ChatRecordService chatRecordService;

    @PostMapping("/chat")
    public void chat(@RequestBody SSEMessage sseMessage) {
        doctorService.chat(sseMessage);
    }

    @GetMapping("/records")
    public List<ChatRecord> getRecords(@RequestBody String userName) {
        return chatRecordService.getRecords(userName);
    }

}