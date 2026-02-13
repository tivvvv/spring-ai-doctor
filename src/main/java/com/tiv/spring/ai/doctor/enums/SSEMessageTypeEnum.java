package com.tiv.spring.ai.doctor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SSE消息类型
 */
@Getter
@AllArgsConstructor
public enum SSEMessageTypeEnum {

    MESSAGE("message", "普通消息"),

    ADD("add", "追加消息,流式推送"),

    DONE("done", "消息推送完成");

    private final String type;

    private final String desc;

}
