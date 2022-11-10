package com.NFT.modules.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Slf4j
public class App {
	public static void main(String[] args)  {
		ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
//		context.getBean(EthereumService.class).blockNumber();
		//context.getBean(SmartContract.class).transfer();
//		context.getBean(EthereumService.class).minerStart();
		//context.close();

	}
}

