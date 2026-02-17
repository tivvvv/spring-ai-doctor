package com.tiv.spring.ai.doctor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tiv.spring.ai.doctor.enums.ChatTypeEnum;
import com.tiv.spring.ai.doctor.mapper.ChatRecordMapper;
import com.tiv.spring.ai.doctor.model.ChatRecord;
import com.tiv.spring.ai.doctor.service.ChatRecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordMapper, ChatRecord> implements ChatRecordService {

    @Resource
    private ChatRecordMapper chatRecordMapper;

    @Override
    public void saveChatRecord(String userName, String message, ChatTypeEnum chatTypeEnum) {
        ChatRecord chatRecord = ChatRecord.builder()
                .content(message)
                .chatType(chatTypeEnum.getType())
                .userName(userName)
                .createTime(new Date())
                .build();
        chatRecordMapper.insert(chatRecord);
    }

    @Override
    public List<ChatRecord> getRecords(String userName) {
        QueryWrapper<ChatRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        return chatRecordMapper.selectList(queryWrapper);
    }

}




