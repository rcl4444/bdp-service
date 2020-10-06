package com.gvt.bdpservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BdpServiceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BdpServiceApplication.class, args);
		while(true) {
			Thread.sleep(1000);
		}
	}
}
