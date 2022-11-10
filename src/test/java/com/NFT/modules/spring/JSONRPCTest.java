package com.NFT.modules.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.geth.Geth;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JSONRPCTest {
    @Autowired
    private Web3j web3j;

    @Autowired
    private Geth geth;

    @Test
    public void TestJSONPRC(){

    }
}
