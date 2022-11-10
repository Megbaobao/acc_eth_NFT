package com.NFT.modules.spring.Controller;


import com.NFT.modules.spring.Service.ERC721.Query.TotalNFTOnContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController

public class TotalNFTContractController {
    @Autowired
    private TotalNFTOnContract totalNFTOnContract;

    @RequestMapping("totalNFTCount")
    public String getTotalNFTOnContract(@RequestParam(value = "contactAddress", required =true) String contractAddress) throws IOException, ExecutionException, InterruptedException {
        return totalNFTOnContract.totalNFTOnContract(contractAddress);
    }
}
