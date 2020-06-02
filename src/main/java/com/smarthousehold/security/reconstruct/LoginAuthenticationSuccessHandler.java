package com.smarthousehold.security.reconstruct;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


public class LoginAuthenticationSuccessHandler  implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setHeader("location","/main/login_success.html");
    }

    /*
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException{
        if (isAjaxRequest(request)) {
            String redirectUrl = buildRedirectUrlToLoginPage(request, response, authException);
            response.setHeader("redirectUrl", redirectUrl);
            System.out.println(redirectUrl);
            response.sendError(HttpServletResponse.SC_FOUND);
        }else {
            super.commence(request, response, authException);
        }
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
    */
}
