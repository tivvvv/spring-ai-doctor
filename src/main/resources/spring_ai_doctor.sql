DROP TABLE IF EXISTS `chat_record`;
CREATE TABLE `chat_record`
(
    `id`          VARCHAR(64) NOT NULL COMMENT '主键id',
    `content`     TEXT        NOT NULL COMMENT '聊天内容',
    `chat_type`   VARCHAR(8)  NOT NULL COMMENT '会话类型',
    `user_name`   VARCHAR(32) NOT NULL COMMENT '用户名',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) COMMENT '聊天记录表';