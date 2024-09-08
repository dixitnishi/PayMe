package com.walletapplication.payme.security;

import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import jakarta.servlet.FilterChain;
import org.springframework.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.mockito.MockedStatic;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.doReturn;

class AuthTokenFilterTest {

    private final JwtUtils jwtUtilsMock = mock(JwtUtils.class, "jwtUtils");

    private final UserDetailsServiceImpl userDetailsServiceMock = mock(UserDetailsServiceImpl.class, "userDetailsService");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks()
    private AuthTokenFilter target;

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }


    @Test()
    void doFilterInternalWhenHeaderAuthNotStartsWithBearer_AndJwtIsNull() throws Exception {
        //Arrange Statement(s)
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        doReturn("C").when(requestMock).getHeader("Authorization");
        FilterChain filterChainMock = mock(FilterChain.class);
        HttpServletResponse httpServletResponseMock = mock(HttpServletResponse.class);
        doNothing().when(filterChainMock).doFilter(requestMock, httpServletResponseMock);
        target = new AuthTokenFilter();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);

        //Act Statement(s)
        target.doFilterInternal(requestMock, httpServletResponseMock, filterChainMock);

        //Assert statement(s)
        assertAll("result", () -> {
            verify(requestMock).getHeader("Authorization");
            verify(filterChainMock).doFilter(requestMock, httpServletResponseMock);
        });
    }

    @Test()
    void doFilterInternalWhenJwtUtilsValidateJwtTokenJwt() throws Exception {
        //Arrange Statement(s)
        HttpServletRequest requestMock = mock(HttpServletRequest.class, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        FilterChain filterChainMock = mock(FilterChain.class, "void");
        HttpServletResponse httpServletResponseMock = mock(HttpServletResponse.class, "void");
        try (MockedStatic<StringUtils> stringUtils = mockStatic(StringUtils.class)) {
            doReturn("{\"Authorization\":{\"value\":\"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\"}}").when(requestMock).getHeader("Authorization");
            doNothing().when(filterChainMock).doFilter(requestMock, httpServletResponseMock);
            stringUtils.when(() -> StringUtils.hasText("{\"Authorization\":{\"value\":\"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\"}}")).thenReturn(false);
            target = new AuthTokenFilter();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            //Act Statement(s)
            target.doFilterInternal(requestMock, httpServletResponseMock, filterChainMock);
            //Assert statement(s)
            assertAll("result", () -> {
                verify(requestMock).getHeader("Authorization");
                verify(filterChainMock).doFilter(requestMock, httpServletResponseMock);
                stringUtils.verify(() -> StringUtils.hasText("{\"Authorization\":{\"value\":\"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\"}}"), atLeast(1));
            });
        }
    }

    @Test()
    void doFilterInternalWhenJwtUtilsValidateJwtTokenJwtAndCaughtException() throws Exception {
        //Arrange Statement(s)
        HttpServletRequest requestMock = mock(HttpServletRequest.class, "{}");
        FilterChain filterChainMock = mock(FilterChain.class, "{}");
        HttpServletResponse httpServletResponseMock = mock(HttpServletResponse.class, "{}");
        RuntimeException runtimeExceptionMock = mock(RuntimeException.class);
        try (MockedStatic<StringUtils> stringUtils = mockStatic(StringUtils.class)) {
            doReturn("String").when(requestMock).getHeader("Authorization");
            doNothing().when(filterChainMock).doFilter(requestMock, httpServletResponseMock);
            stringUtils.when(() -> StringUtils.hasText("String")).thenThrow(runtimeExceptionMock);
            target = new AuthTokenFilter();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            //Act Statement(s)
            target.doFilterInternal(requestMock, httpServletResponseMock, filterChainMock);
            //Assert statement(s)
            assertAll("result", () -> {
                verify(requestMock).getHeader("Authorization");
                verify(filterChainMock).doFilter(requestMock, httpServletResponseMock);
                stringUtils.verify(() -> StringUtils.hasText("String"), atLeast(1));
            });
        }
    }
}
