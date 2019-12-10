package com.ifi.trainer_api.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityConfigTest {

    @Test
    void securityConfig_shouldBeAConfiguration(){
        assertNotNull(SecurityConfig.class.getAnnotation(Configuration.class));
    }

    @Test
    void securityConfig_shouldExtendWebSecurityConfigurerAdapter(){
        assertTrue(WebSecurityConfigurerAdapter.class.isAssignableFrom(SecurityConfig.class));
    }

}
