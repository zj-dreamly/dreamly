package com.github.zj.dreamly.sms.aliyun.exp;

import lombok.Getter;

/**
 * 短信发送异常
 *
 * @author fengshuonan
 * @date 2018-07-06-下午3:00
 */
@Getter
public class AliyunSmsException extends RuntimeException {

    private final String code;

    private final String errorMessage;

    public AliyunSmsException(String code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
