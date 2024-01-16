package com.walletapplication.payme.security;

import org.mockito.InjectMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import com.walletapplication.payme.utils.ControllerPathValidator;
import org.mockito.MockitoAnnotations;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;


class AuthEntryPointJwtTest {

    private final ControllerPathValidator controllerPathValidatorMock = mock(ControllerPathValidator.class, "controllerPathValidator");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks()
    private AuthEntryPointJwt target;

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }


    @Test()
    void commenceWhenStatusNotEqualsHttpServletResponseSC_UNAUTHORIZED() throws IOException {
        //Arrange Statement(s)
        HttpServletRequest requestMock = mock(HttpServletRequest.class, "/test");
        doReturn("String").when(requestMock).getServletPath();
        HttpServletResponse responseMock = mock(HttpServletResponse.class, "ServletOutputStream");
        doNothing().when(responseMock).setStatus(404);
        doNothing().when(responseMock).setContentType("application/json");
        ServletOutputStream servletOutputStreamMock = mock(ServletOutputStream.class, "ServletOutputStream");
        doReturn(servletOutputStreamMock).when(responseMock).getOutputStream();
        AuthenticationException authExceptionMock = mock(AuthenticationException.class, "Unauthorized");
        doReturn("String").when(authExceptionMock).getMessage();
        target = new AuthEntryPointJwt();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        doReturn(false).when(controllerPathValidatorMock).isValidPath("String");

        //Act Statement(s)
        target.commence(requestMock, responseMock, authExceptionMock);

        //Assert statement(s)
        assertAll("result", () -> {
            verify(requestMock).getServletPath();
            verify(responseMock).setStatus(404);
            verify(responseMock).setContentType("application/json");
            verify(responseMock).getOutputStream();
            verify(authExceptionMock).getMessage();
            verify(controllerPathValidatorMock).isValidPath("String");
        });
    }


    @Test()
    void commenceWhenStatusEqualsHttpServletResponseSC_UNAUTHORIZED() throws IOException {
        String string = null;
        //Arrange Statement(s)
        HttpServletRequest requestMock = mock(HttpServletRequest.class, "");
        doReturn("").when(requestMock).getServletPath();
        HttpServletResponse responseMock = mock(HttpServletResponse.class, "");
        doNothing().when(responseMock).setStatus(401);
        doNothing().when(responseMock).setContentType("application/json");
        ServletOutputStream servletOutputStreamMock = mock(ServletOutputStream.class, "");
        doReturn(servletOutputStreamMock).when(responseMock).getOutputStream();
        AuthenticationException authExceptionMock = mock(AuthenticationException.class, "");
        doReturn("", string).when(authExceptionMock).getMessage();
        target = new AuthEntryPointJwt();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        doReturn(true).when(controllerPathValidatorMock).isValidPath("");

        //Act Statement(s)
        target.commence(requestMock, responseMock, authExceptionMock);

        //Assert statement(s)
        assertAll("result", () -> {
            verify(requestMock).getServletPath();
            verify(responseMock).setStatus(401);
            verify(responseMock).setContentType("application/json");
            verify(responseMock).getOutputStream();
            verify(authExceptionMock, times(2)).getMessage();
            verify(controllerPathValidatorMock).isValidPath("");
        });
    }
}
