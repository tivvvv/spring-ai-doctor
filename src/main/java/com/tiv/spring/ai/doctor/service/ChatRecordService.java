package com.tiv.spring.ai.doctor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiv.spring.ai.doctor.enums.ChatTypeEnum;
import com.tiv.spring.ai.doctor.model.ChatRecord;

public interface ChatRecordService extends IService<ChatRecord> {

    void saveChatRecord(String userName, String message, ChatTypeEnum chatTypeEnum);

}
