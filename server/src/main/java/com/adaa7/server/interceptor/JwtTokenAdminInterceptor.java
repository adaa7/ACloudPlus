package com.adaa7.server.interceptor;

import com.adaa7.common.context.BaseContext;
import com.adaa7.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
@Component

public class JwtTokenAdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        String token = request.getHeader("Authorization");
        try {
            Claims claims = JwtUtil.getClaimsFromJwt(token);
            Integer userId = Integer.valueOf(claims.get("id").toString());
            BaseContext.setCurrentId(userId);
            return true;
        }catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}
