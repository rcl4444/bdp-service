package com.gvt.bdpservice.configuration;

import java.net.InetAddress;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class XXLJobConfiguration {
    @Autowired
    XXLJobExecutorConfig xxlJobExecutorConfig;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() throws Exception {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobExecutorConfig.getAddresses());
        xxlJobSpringExecutor.setAppName(xxlJobExecutorConfig.getAppName());
        if (StringUtils.isEmpty(xxlJobExecutorConfig.getIp())) {
            String ip = InetAddress.getLocalHost().getHostAddress();
            xxlJobSpringExecutor.setIp(ip);
        } else {
            xxlJobSpringExecutor.setIp(xxlJobExecutorConfig.getIp());
        }
        xxlJobSpringExecutor.setPort(xxlJobExecutorConfig.getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobExecutorConfig.getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobExecutorConfig.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobExecutorConfig.getLogRetentionDays());
        return xxlJobSpringExecutor;
    }
}
