package com.tiv.spring.ai.doctor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiv.spring.ai.doctor.enums.ChatTypeEnum;
import com.tiv.spring.ai.doctor.mapper.ChatRecordMapper;
import com.tiv.spring.ai.doctor.model.ChatRecord;
import com.tiv.spring.ai.doctor.service.ChatRecordService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordMapper, ChatRecord> implements ChatRecordService {

    @Override
    public void saveChatRecord(String userName, String message, ChatTypeEnum chatTypeEnum) {
        ChatRecord chatRecord = ChatRecord.builder()
                .content(message)
                .chatType(chatTypeEnum.getType())
                .userName(userName)
                .createTime(new Date())
                .build();
        this.save(chatRecord);
    }

}




