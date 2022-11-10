package com.NFT.modules.spring;


import com.NFT.modules.spring.Service.ERC721.Receipt.GetApprovalInfoService;
import com.NFT.modules.spring.Service.ERC721.Receipt.GetTokenIdInfoService;

import com.NFT.modules.spring.Service.ERC721.Receipt.GetTransferInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.geth.Geth;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EventTest {

    @Autowired
    private Geth geth;
    @Autowired
    private GetTokenIdInfoService getTokenIdInfoService;

    @Autowired
    private GetTransferInfoService getTransferInfoService;


    @Autowired
    private GetApprovalInfoService getApprovalInfoService;
    private BigInteger filterID;


    @Test
    public void testGetTokenId() throws IOException, ExecutionException, InterruptedException {
        BigInteger tokenId = getTokenIdInfoService.GetFilterChanges("0x5a432b17148733becd3fc3037037148d17fbd7e3");
        System.out.println(tokenId);
    }

//    @Test
//    public void testGetTransfer() throws InterruptedException, ExecutionException {
//        Future<Map> futureMap = getTransferInfoService.GetFilterChanges("0xfbA21484b8b1BeAa00795827B21F25eBC65Dd786");
//        System.out.println(futureMap.get());
//    }

    @Test
    public void testGetApproval() throws InterruptedException, ExecutionException {
        Future<Map> futureMap = getApprovalInfoService.GetFilterChanges("0xfbA21484b8b1BeAa00795827B21F25eBC65Dd786");
        System.out.println(futureMap.get());
    }

    public BigInteger testNewFilter(String contractAddress){
        System.out.println("contractAddress"+contractAddress);
        DefaultBlockParameter fromBlock = DefaultBlockParameter.valueOf(new BigInteger("5000"));
        DefaultBlockParameter toBlock = DefaultBlockParameter.valueOf(new BigInteger("5200"));
        EthFilter ethFilter=new EthFilter(fromBlock, toBlock, contractAddress);
        geth.ethNewFilter(ethFilter).observable().subscribe(result -> {
            if(result.hasError()) {
                System.out.println(result.getError().getMessage());
            } else {
                System.out.println(result.getFilterId());
                filterID=result.getFilterId();

            }
        });
        return filterID;
    }


    @Test
    public void testEtheGetlogs() throws InterruptedException {
        EthFilter newFilterID = new EthFilter();
        geth.ethGetLogs(newFilterID).observable().subscribe(result->{
            if (result.hasError()){
                System.out.println(result.getError().getMessage());
            }else{
                System.out.println(result.getLogs());
            }
        });
    }
    

    @Test
    public void getTransactionReceipt() throws IOException, InterruptedException {
        HashMap<Object, Object> map = getTransferInfoService.receiptListening("0x0cd30bf49d4737be4d8a75ffed9421a7dd964b896e7144d6773cf0f823ad60fd");
        System.out.println(map);
    }

    @Test
    public void TestTransactionReceipt(){
        geth.ethGetTransactionReceipt("0x725ac31cc63f58264350b530699e710bd8932b107cc9658d4ed0f2097e27020d").observable().subscribe(receipt -> {
        Optional<TransactionReceipt> optional = receipt.getTransactionReceipt();
        if (optional.isPresent()) {
            BigInteger blockNumber = optional.get().getBlockNumber();
            System.out.println("blockNumber"+blockNumber);
//            Date time = blockTimeService.getBlockTime(blockNumber);
//            map.put("time",time);
        }
    });
    }
}



