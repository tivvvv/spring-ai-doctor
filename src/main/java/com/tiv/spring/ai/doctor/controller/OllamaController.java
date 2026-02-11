package com.tiv.spring.ai.doctor.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ollama")
public class OllamaController {

    @Resource
    OllamaChatClient ollamaChatClient;

    @GetMapping("/chat")
    public Object chat(@RequestParam String msg) {
        return ollamaChatClient.call(msg);
    }

}