package com.github.zj.dreamly.tool.configuration;

import com.github.zj.dreamly.tool.properties.CorsProperties;
import com.github.zj.dreamly.tool.properties.RequestProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h2>RequestFilter</h2>
 *
 * @author: 苍海之南
 * @since: 2019-09-19 10:27
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(value = "dreamly.tool.request.enabled", havingValue = "true")
@EnableConfigurationProperties({RequestProperties.class})
public class RequestFilter extends GenericFilterBean {

    private static final String PREFIX = "/webjars";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
						 FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String uri = request.getRequestURI();
        if (uri.startsWith(PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        final long startTime = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        log.info("【本次请求地址】：{}|{}, 【耗时】：{}ms", request.getMethod(), request.getRequestURI(),
                System.currentTimeMillis() - startTime);

    }
}
