package com.NFT.modules.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;
import org.web3j.spring.autoconfigure.Web3jProperties;

@Configuration
public class Config {

	@Autowired
	private Web3jProperties web3jProperties;
	
	@Bean
	public Geth get() {
		return Geth.build(new HttpService(web3jProperties.getClientAddress()));
	}
}


