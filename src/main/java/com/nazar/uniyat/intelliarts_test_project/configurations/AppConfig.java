package com.nazar.uniyat.intelliarts_test_project.configurations;


import com.nazar.uniyat.intelliarts_test_project.components.RatesMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class AppConfig {

    @Bean
    public RatesMapper ratesMapper() {
        return new RatesMapper();
    }

}
