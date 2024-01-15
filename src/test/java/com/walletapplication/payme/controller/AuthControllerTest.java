package com.walletapplication.payme.controller;

import com.walletapplication.payme.model.inbound.AccountRequest;
import com.walletapplication.payme.model.inbound.LoginRequest;
import com.walletapplication.payme.model.outbound.AccountResponse;
import com.walletapplication.payme.model.outbound.LoginResponse;
import com.walletapplication.payme.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @Test
    void testSignup() throws Exception {


        AccountRequest accountRequest = AccountRequest.builder()
                .email("nishi@gmail.com")
                .name("Nishi")
                .password("Nishi")
                .build();
        AccountResponse mockResponse = new AccountResponse(LocalDateTime.now(),"1000001","Logged in successfully","Nishi","nishi@gmail.com",0.00,"mocktoken"); // Provide a mock response

        when(authenticationService.signup(any(AccountRequest.class))).thenReturn(mockResponse);

        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nishi\",\"email\":\"nishi@gmail.com\",\"password\":\"Nishi@123\"}")) // Provide appropriate JSON content for the request
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.yourJsonKey").value("expectedValue")); // Adjust this based on your actual response structure

        verify(authenticationService, times(1)).signup(any(AccountRequest.class));
        verifyNoMoreInteractions(authenticationService);
    }

    @Test
    public void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest(); // Provide appropriate data for the request
        LoginResponse mockResponse = new LoginResponse(); // Provide a mock response

        when(authenticationService.login(any(LoginRequest.class))).thenReturn(mockResponse);

        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"key\":\"value\"}")) // Provide appropriate JSON content for the request
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yourJsonKey").value("expectedValue")); // Adjust this based on your actual response structure

        verify(authenticationService, times(1)).login(any(LoginRequest.class));
        verifyNoMoreInteractions(authenticationService);
    }
}
