package com.app.todoapp.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 1. 从 Header 里拿 token
        String authHeader = request.getHeader("Authorization");

        // 2. token 为空 → 返回 401
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;  // 拦截，不放行
        }

        // 3. 截取掉 "Bearer " 前缀，拿到纯 token
        String token = authHeader.substring(7);

        // 4. 验证 token 是否合法
        if (!jwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;  // 拦截，不放行
        }

        // 5. 合法 → 把 userId 存入 request，放行
        Long userId = jwtUtil.getUserIdFromToken(token);
        request.setAttribute("userId", userId);
        return true;  // 放行
    }
}
