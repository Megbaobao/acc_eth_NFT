package com.NFT.modules.spring;


import com.NFT.modules.spring.Service.ERC20.Query.GetAllowanceService;
import com.NFT.modules.spring.Service.ERC20.Query.GetBalanceService;
import com.NFT.modules.spring.Service.ERC20.Query.GetTotalSupplyService;
import com.NFT.modules.spring.Service.ERC721.Query.TotalNFTOnContract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.geth.Geth;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class QueryTest {

    @Autowired
    private Geth geth;

    @Autowired
    private Web3j web3j;

    @Autowired
    private TotalNFTOnContract totalNFTOnContract;

    @Autowired
    private GetTotalSupplyService  getTotalSupplyService;

    @Autowired
    private GetBalanceService getBalanceService;

    @Autowired
    private GetAllowanceService getAllowanceService;
    /**
     *ERC721 NFT
     */

    @Test
    public void testQueryTotalNFT() throws IOException, ExecutionException, InterruptedException {
        String count = totalNFTOnContract.totalNFTOnContract("0x1CEf14B3Db4424a7e8bC6f227412C62B72738d1C");
        System.out.println(count);
    }

    /**
     *ERC20 Token
     */

    @Test
    public void testGetInitailSupply() throws IOException, InterruptedException {
        BigInteger initialSupply = getTotalSupplyService.getTotalSupply("0x0d915853e29a81b8b3203756f752d28f792de62c", "0x3955a0a2cb9cb91e8fc6a45e70d4520c51094c3d");
        System.out.println(initialSupply);
    }


    @Test
    public void testGetPersonalBalance() throws IOException, InterruptedException {
        BigInteger balance = getBalanceService.getBalance("0x54c2ce87414a376a38e40b28ff73069541af206a", "0xF1F11B64DCbBDab9091366d3cf6Fdf55993295dd");
        System.out.println(balance );
    }


    @Test
    public void testGetAllowance() throws IOException, InterruptedException {
        BigInteger allowance = getAllowanceService.getAllowance("0x0D915853E29A81B8B3203756F752d28F792DE62c", "0x54c2ce87414a376a38e40b28ff73069541af206a", "0xF1F11B64DCbBDab9091366d3cf6Fdf55993295dd");
        System.out.println(allowance);
    }

}

