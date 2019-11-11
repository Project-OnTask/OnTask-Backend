package com.itsfive.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//This configuration is used to generate createdAt and updatedAt fields in every table. 
@Configuration
@EnableJpaAuditing
public class AuditingConfig {

}
