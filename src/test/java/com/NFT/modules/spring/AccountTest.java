package com.NFT.modules.spring;

import com.NFT.modules.spring.Service.Account.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AccountTest {

    

    @Autowired
    private AccountService accountService;


    @Test
    public void testUnlockAccount(){

        accountService.unlockAccount("0x54c2ce87414a376a38e40b28ff73069541af206a","123");

    }

    @Test
    public void testGetNumberOFNFTS() throws IOException {
        BigInteger count = accountService.numberOfNFTS("0xbafe9d02bd556d3bc91539bbc60603a01d18dddd", "0xfbA21484b8b1BeAa00795827B21F25eBC65Dd786");
        System.out.println(count);
    }

    @Test
    public void getReturnValues() throws IOException {
        BigInteger count = accountService.numberOfNFTS("0x0D915853E29A81B8B3203756F752d28F792DE62c", "0x0d123B8D9cd5497abD0bfFB397FC3ff8e4f725d5");
        System.out.println(count);
    }

    @Test
    public void testGetKey() throws Exception {
        //428a055a1f3d59b35cf49534d14cef24e9a6a6f0b820bad11720888b124f37da
        Credentials credentials = WalletUtils.loadCredentials("123", new File("/Users/mac/eth_node_private/node1/keystore/UTC--2022-09-01T03-01-01.657109000Z--679bf01e1b6666c64407a6dc303f1ee2e52de926.json"));
        System.out.println(credentials.getAddress());
        System.out.println(credentials.getEcKeyPair().getPrivateKey().toString(16));
    }

    @Test
    public void  testGetBalance() throws IOException {
        System.out.println(accountService.getBalance("0xbafe9d02bd556d3bc91539bbc60603a01d18dddd"));
    }

//    @Test
//    public void testEthTransfer() throws ExecutionException, InterruptedException {
//        accountService.ethTransfer("0x0D915853E29A81B8B3203756F752d28F792DE62c","0x54c2ce87414a376a38e40b28ff73069541af206a", 100);
//    }
}

