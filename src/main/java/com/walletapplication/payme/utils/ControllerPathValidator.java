package com.walletapplication.payme.utils;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j(topic = "ControllerPathValidator")
@Component
public class ControllerPathValidator {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    private final Set<String> validPaths = new HashSet<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @PostConstruct
    public void initialize() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();
            if (mappingInfo.getPathPatternsCondition() != null) {
                Set<String> patterns = mappingInfo.getPathPatternsCondition().getPatterns()
                        .stream()
                        .map(PathPattern::toString)
                        .filter(pattern -> !pattern.equals("/error"))
                        .collect(Collectors.toSet());
                log.info("Controllers {}",patterns);
                validPaths.addAll(patterns);
            }
        }
    }

    public boolean isValidPath(String path) {
        return validPaths.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}
