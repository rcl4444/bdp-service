package com.gvt.bdpservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "xxl.job.executor")
public class XXLJobExecutorConfig {
    private String addresses;

    private String appName;

    private String ip;

    private int port;

    private String accessToken;

    private String logPath;

    private Integer logRetentionDays;
}
