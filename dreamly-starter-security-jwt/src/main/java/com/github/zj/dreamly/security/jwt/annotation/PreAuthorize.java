package com.github.zj.dreamly.security.jwt.annotation;

import com.github.zj.dreamly.security.jwt.el.PreAuthorizeExpressionRoot;

import java.lang.annotation.*;

/**
 * 用于认证、鉴权的注解
 * @author itmuch.com
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuthorize {
    /**
     * @return 表达式
     * @see PreAuthorizeExpressionRoot
     */
    String value();
}
