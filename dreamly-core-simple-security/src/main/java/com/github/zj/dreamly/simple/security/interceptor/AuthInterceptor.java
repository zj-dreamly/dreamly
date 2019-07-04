package com.github.zj.dreamly.simple.security.interceptor;

import com.github.zj.dreamly.simple.security.el.PreAuthorizeExpressionRoot;
import com.github.zj.dreamly.simple.security.spec.Spec;
import com.github.zj.dreamly.simple.security.util.SpringElCheckUtil;
import com.github.zj.dreamly.tool.exception.DreamlySecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.github.zj.dreamly.simple.security.util.RestfulMatchUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 授权控制拦截器
 *
 * @author 苍海之南
 */
@RequiredArgsConstructor
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private final List<Spec> specList;
    private final PreAuthorizeExpressionRoot preAuthorizeExpressionRoot;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 当前请求的路径和定义的规则能够匹配
        Boolean checkResult = specList.stream()
                .filter(spec -> RestfulMatchUtil.match(request, spec.getHttpMethod(), spec.getPath()))
                .findFirst()
                .map(spec -> {
                    String expression = spec.getExpression();
                    return SpringElCheckUtil.check(
                            new StandardEvaluationContext(preAuthorizeExpressionRoot),
                            expression
                    );

                })
                .orElse(true);
        if (!checkResult) {
            throw new DreamlySecurityException("Access Denied.");
        }
        return true;
    }
}

