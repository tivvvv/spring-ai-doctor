package com.tiv.spring.ai.doctor.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 聊天记录表
 *
 * @TableName chat_record
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "chat_record")
public class ChatRecord {

    /**
     * 主键id
     */
    @TableId
    private String id;

    /**
     * 聊天内容
     */
    private String content;

    /**
     * 会话类型
     */
    private String chatType;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 创建时间
     */
    private Date createTime;

}