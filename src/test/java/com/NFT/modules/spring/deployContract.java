package com.NFT.modules.spring;

import com.NFT.modules.spring.Service.ERC20.DeployContract.ERC20ContractService;
import com.NFT.modules.spring.Service.ERC721.DeployContract.ERC721contractService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class deployContract {

    @Autowired
    private ERC721contractService erc721contractService;

    @Autowired
    private ERC20ContractService erc20ContractService;



    @Test
    public void TestERC721contractService() throws IOException, InterruptedException {
        String s = erc721contractService.deployContract();
        String contractAddress = erc721contractService.contractAddress();
    }

    @Test
    public void TestERC20contractService() throws IOException, InterruptedException {
        String s = erc20ContractService.deployContract();
        String contractAddress = erc20ContractService.contractAddress();
    }
}
