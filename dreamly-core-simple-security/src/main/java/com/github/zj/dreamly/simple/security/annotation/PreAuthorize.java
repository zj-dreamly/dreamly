package com.github.zj.dreamly.simple.security.annotation;

import java.lang.annotation.*;

/**
 * 用于认证、鉴权的注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuthorize {
    /**
     * @return 表达式
     * @see com.github.zj.dreamly.simple.security.el.PreAuthorizeExpressionRoot
     */
    String value();
}
