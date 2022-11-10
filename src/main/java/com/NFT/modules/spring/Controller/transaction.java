//package com.NFT.modules.spring.controller;

import com.NFT.modules.spring.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

//@RestController
//@RequestMapping("/smartContract")
//public class transaction {
//    @Autowired
//    private SmartContract smartContract;
//
//    @Autowired
//    private GameItem gameItem;
//
//    @Autowired
//    private ERC721contractService contract;
//
//
//    @Autowired
//    private GetTokenIdInfoService id;
//
//    @Autowired
//    private com.NFT.modules.spring.Service.transaction trans;
//
//    //callFunction 固定化
//    @RequestMapping("/transfer")
//    public String transfer(@RequestParam("value")String value, @RequestParam("caller")String caller,
//                           @RequestParam("contractAddress")String contractAddress,@RequestParam("callingFunction")String callingFunction,
//                           @RequestParam("receiver")String receiver) throws IOException {
//
//        return smartContract.transfer(value,caller,contractAddress, callingFunction,receiver);
//
//    }
//
//
//    //
//    @RequestMapping("/listeningBlock")
//    public List listeningBlock(@RequestParam("contractAddress") String contactAddress) throws Exception {
//       return smartContract.EthLogObservable(contactAddress);
//    }
//
//    @RequestMapping("/forgeNFT")
//    public String forgeNFT( @RequestParam("callingAddress") String callingAddress, @RequestParam("contactAddress") String contactAddress, @RequestParam("url") String url, @RequestParam("playerAddress") String playerAddress) throws IOException {
//        return smartContract.ntf(callingAddress, contactAddress, url, playerAddress);
//    }
//
//    @RequestMapping("/contractAddress")
//    public String contractAddress(@RequestParam("transactionHash") String transactionHash){
//        return gameItem.GetContractAddress(transactionHash);
//    }
//
//    @RequestMapping("/listeningContract")
//    public void listeningContract(@RequestParam("contractAddress") String contractAddress){
//        gameItem.EthGetLogs(contractAddress);
//    }
//
//    @RequestMapping("/deployContract")
//    public String deployContract() throws IOException {
//        return contract.deploy();
//    }
//
//    @RequestMapping("/tokenId")
//    public String tokenId(@RequestParam("fromBlock") String fromBlock, @RequestParam("contractAddress") String contractAddress) throws IOException, ExecutionException, InterruptedException {
//        return id.getTokenId(fromBlock, contractAddress);
//    }
//
//    @RequestMapping("/transfertoken")
//    public String transfertransfer(@RequestParam("contractAddress") String contractAddress, @RequestParam("toAddress") String toAddress, @RequestParam("tokenId") String tokenId) throws IOException {
//        return trans.transfertoken(contractAddress,toAddress,tokenId);
//    }
//}



