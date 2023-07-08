package com.xiaoyao.hxds.message.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {
    @Bean
    public Jackson2JsonMessageConverter rabbitTemplateCustomizer() {
        return new Jackson2JsonMessageConverter();
    }
}
