//package com.example.accelerator.exception;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Controller;
//
//import javax.naming.AuthenticationException;
//
//@Controller
//public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest request,
//                         HttpServletResponse response,
//                         AuthenticationException authException) {
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        try {
//            response.getWriter().write("{\"error\": \"Wrong username or password \"}");
//        } catch (java.io.IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
