package com.tiv.spring.ai.doctor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatTypeEnum {

    USER("user", "用户发送的内容"),

    AI("ai", "AI回复的内容");

    private final String type;

    private final String desc;

}