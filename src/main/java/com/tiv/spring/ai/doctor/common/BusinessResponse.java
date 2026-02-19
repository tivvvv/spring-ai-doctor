package com.tiv.spring.ai.doctor.common;

import com.tiv.spring.ai.doctor.enums.BusinessCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 业务响应封装类
 */
@Data
@AllArgsConstructor
public class BusinessResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BusinessResponse(BusinessCodeEnum businessCodeEnum) {
        this(businessCodeEnum.getCode(), null, businessCodeEnum.getMessage());
    }

    public BusinessResponse(BusinessCodeEnum businessCodeEnum, T data) {
        this(businessCodeEnum.getCode(), data, businessCodeEnum.getMessage());
    }

}
