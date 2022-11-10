package com.NFT.modules.spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("accounts")
public class Controller {

    @Autowired
    private EthereumService ethereumService;


    @GetMapping(value = "/getBlockNumber")
    public BigInteger getBlockNumber() throws Exception {return ethereumService.blockNumber();
    }

    @GetMapping(value="/getAccounts")
    public List<String> getAccounts() throws IOException {
        List<String> accounts = ethereumService.getAccounts();
        return accounts;
    }
    @GetMapping(value="createAccount")
    public String createAccount() throws IOException {
        String accounts = ethereumService.createAccounts();
        return accounts;
    }



}

