package com.NFT.modules.spring.Service.Block;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;

import java.math.BigInteger;

@Service
public class block {
    @Autowired
    private Web3j web3j;

    public BigInteger blockNumber() throws Exception {
        EthBlockNumber bn = web3j.ethBlockNumber().send();
        if(bn.hasError()) {
            System.out.println(bn.getError().getMessage());
        } else {
            System.out.println(bn.getBlockNumber());
        }
        return bn.getBlockNumber();
    }
}


