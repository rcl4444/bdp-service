package com.gvt.bdpservice.configuration;

import com.xxl.job.core.executor.XxlJobExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class XxlJobExecutorCommandLineRunner implements CommandLineRunner {
    @Autowired
    XxlJobExecutor xxlJobExecutor;

    @Override
    public void run(String... args) throws Exception {
        xxlJobExecutor.start();
    }
}
