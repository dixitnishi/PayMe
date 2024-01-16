package com.walletapplication.payme.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletapplication.payme.utils.ControllerPathValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j(topic = "AuthEntryPointJwt")
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Autowired
    private ControllerPathValidator controllerPathValidator;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage());
        String requestedPath = request.getServletPath();
        if (!controllerPathValidator.isValidPath(requestedPath)) {
            handleResourceNotFound(response, requestedPath);
        } else {
            handleUnauthorized(response, authException, requestedPath);
        }
    }

    private void handleUnauthorized(HttpServletResponse response, AuthenticationException authException, String path) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final Map<String, Object> body = createErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage(), path);
        writeJsonResponse(response, body);
    }

    private void handleResourceNotFound(HttpServletResponse response, String path) throws IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final Map<String, Object> body = createErrorResponse(HttpServletResponse.SC_NOT_FOUND, "Resource not found", path);
        writeJsonResponse(response, body);
    }

    private Map<String, Object> createErrorResponse(int status, String errorMessage, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status);
        body.put("error", status == HttpServletResponse.SC_UNAUTHORIZED ? "Unauthorized" : "Not Found");
        body.put("message", errorMessage);
        body.put("path", path);
        return body;
    }

    private void writeJsonResponse(HttpServletResponse response, Map<String, Object> body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }


}
