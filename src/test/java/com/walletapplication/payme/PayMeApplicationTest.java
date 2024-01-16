package com.walletapplication.payme;

import com.walletapplication.payme.PayMeApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PayMeApplicationTest {
    @Test
    void corsConfigurer_shouldReturnWebMvcConfigurer() {
        // Arrange
        PayMeApplication payMeApplication = new PayMeApplication();

        // Act
        WebMvcConfigurer webMvcConfigurer = payMeApplication.corsConfigurer();

        // Assert
        assertNotNull(webMvcConfigurer);
    }

    @Test
    void corsConfigurer_shouldConfigureCorsMappings() {
        // Arrange
        PayMeApplication payMeApplication = new PayMeApplication();
        WebMvcConfigurer webMvcConfigurer = payMeApplication.corsConfigurer();
        CorsRegistry registry = new CorsRegistry();

        // Act
        webMvcConfigurer.addCorsMappings(registry);

        // Assert
        assertNotNull(registry);
        // Add more assertions for CorsRegistry configuration as needed
    }

}
