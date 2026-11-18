package br.com.bookstore.config;

import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (uri.startsWith("/api/auth/") || uri.equals("/login.html") || uri.equals("/api/status")) return true;
        if (uri.startsWith("/css/") || uri.startsWith("/js/") || uri.equals("/favicon.ico")) return true;
        if (request.getSession(false) != null && request.getSession(false).getAttribute("usuarioId") != null) return true;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"erro\":\"Faça login para continuar.\"}");
        return false;
    }
}
