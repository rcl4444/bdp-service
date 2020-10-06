package com.gvt.bdpservice.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@JobHandler("SyncJobHandler")
public class SyncJobHandler extends IJobHandler {

    @Slf4j
    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private InputStream errorStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, InputStream errorStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.errorStream = errorStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            try {
                new BufferedReader(new InputStreamReader(new SequenceInputStream(inputStream, errorStream),
                        Charset.defaultCharset().name())).lines().forEach(consumer);
            } catch (UnsupportedEncodingException e) {
                log.error("process input stream error", e);
            }
        }
    }

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        ProcessBuilder builder = new ProcessBuilder();
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        if (isWindows) {
            builder.command("cmd.exe", "/c", "chcp 65001 | dir");
        } else {
            builder.command("sh", "-c", "ls");
        }
        builder.directory(new File(System.getProperty("user.home")));
        Process process = builder.start();
        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), process.getErrorStream(),
                System.out::println);
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        try {
            if (process.waitFor() == 0) {
                return new ReturnT<String>(null);
            } else {
                return new ReturnT<String>(500, null);
            }
        } catch (Exception e) {
            return new ReturnT<String>(500, e.getMessage());
        } finally {
            process.destroy();
        }
    }
}
