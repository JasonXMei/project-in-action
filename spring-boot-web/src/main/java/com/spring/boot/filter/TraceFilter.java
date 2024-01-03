package com.spring.boot.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@Slf4j
public class TraceFilter extends OncePerRequestFilter {

    public static final String TRACE_KEY = "trace-id";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String traceId = generateTraceId();
        MDC.put(TRACE_KEY, traceId);
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            log.info("done");
            MDC.clear();
        }
    }

    private static String generateTraceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
