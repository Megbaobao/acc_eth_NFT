package com.NFT.modules.spring.Service.ERC721.Receipt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.geth.Geth;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class GetTransferInfoService {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Geth geth;
    private BigInteger filterId;
    private String ownerAddress;
    private String TokenID;



//    @Autowired
//    private TransferData transferData;


//    public BigInteger testNewFilter(String contractAddress){
//        System.out.println("contractAddress"+contractAddress);
//        DefaultBlockParameter fromBlock = DefaultBlockParameter.valueOf(new BigInteger("2000"));
//        EthFilter ethFilter=new EthFilter(fromBlock, null, contractAddress);
//        geth.ethNewFilter(ethFilter).observable().subscribe(result -> {
//            if(result.hasError()) {
//                System.out.println(result.getError().getMessage());
//            } else {
//                System.out.println(result.getFilterId());
//                filterId=result.getFilterId();
//
//            }
//        });
//        return filterId;
//    }
//
//
//    @Async
//    public Future<Map> GetFilterChanges(String contractAddress) throws InterruptedException {
//        HashMap<Object, Object> map = new HashMap<>();
////        List<String> indexedValues = new ArrayList<>();
////        BigInteger newFilterID=testNewFilter(contractAddress);
//        BigInteger newFilterID=testNewFilter(contractAddress);
//        System.out.println("newFilterID"+newFilterID);
//        Thread.sleep(20000);
//        System.out.println("查看filterlogs");
//
//        geth.ethGetFilterChanges(newFilterID).observable().subscribe(result -> {
//            if(result.hasError()) {
//                System.out.println(result.getError().getMessage());
//            } else {
//                List<EthLog.LogResult> logList = result.getLogs();
//                System.out.println(logList);
//                if (logList.isEmpty()) {
//                }
//                else {
//                    EthLog.LogObject Log = (EthLog.LogObject) result.getLogs().get(0);
//                    System.out.println("LogObject" + Log);
//                    List<String> topics = Log.getTopics();
//                    String _to = topics.get(2);
//                    String tokenId = topics.get(3);
//                    List<TypeReference<?>> NonIndexedParameters = Arrays.asList();
//                    List<TypeReference<?>> IndexedParameters = Arrays.asList(new TypeReference<Address>() {
//                    }, new TypeReference<Address>() {
//                    }, new TypeReference<Uint256>() {
//                    });
//                    Event event = new Event("Transfer", IndexedParameters, NonIndexedParameters);
//
//
////                    for (int i = 0; i < IndexedParameters.size(); i++) {
////                        Type value = FunctionReturnDecoder.decodeIndexedValue(topics.get(i + 1), IndexedParameters.get(i));
////                        indexedValues.add(value);
////                    }
////                    IndexedValues=indexedValues;
//
//                    /**
//                     * ownerAddress
//                     */
//
//                    List<TypeReference<Type>> IndexedParameters_to = new ArrayList<>();
//                    IndexedParameters_to.add(event.getIndexedParameters().get(1));
//                    List<Type> ownerAddress_list = FunctionReturnDecoder.decode(_to, IndexedParameters_to);
//                    ownerAddress = (String) ownerAddress_list.get(0).getValue();
////                    indexedValues.add(ownerAddress);
//                    map.put("ownerAddress", ownerAddress);
//                    /**
//                     * TokenId
//                     */
//
//                    List<TypeReference<Type>> IndexedParameters_tokenId = new ArrayList<>();
//                    IndexedParameters_tokenId.add(event.getIndexedParameters().get(2));
//                    List<Type> tokenId_list = FunctionReturnDecoder.decode(tokenId, IndexedParameters_tokenId);
//                    TokenID = String.valueOf((BigInteger) tokenId_list.get(0).getValue());
////                    indexedValues.add(TokenID);
//                    map.put("TokenID", TokenID);
////                    transferData.setOwnerAddress((String) map.get("ownerAddress"));
////                    transferData.setTokenId((String) map.get("TokenID"));
////                    System.out.println(map.get("TokenID"));
//
//                    System.out.println("maporiginal"+map);
//                }
//            }
//        });
//
//
//        return  new AsyncResult<Map>(map);
//    }


    public HashMap<Object, Object> receiptListening(String transactionHash) throws IOException, InterruptedException {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("transactionHash", transactionHash);
        Optional<TransactionReceipt> receipt = null;

//        Queue<EthGetTransactionReceipt> blockingQueue =  new LinkedList<>();
//        Optional<TransactionReceipt> receipt = web3j.ethGetTransactionReceipt(transactionHash).send();
//        blockingQueue.add(receipt);
//        EthGetTransactionReceipt receiptFromQueue = blockingQueue.poll();
//        System.out.println("receiptFromQueue"+receiptFromQueue.getTransactionReceipt());
//        System.out.println(receiptFromQueue.getTransactionReceipt().get().getLogs());
//        EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt(transactionHash).send();

        long timeout = 50000L;
        long t0 = System.currentTimeMillis();


        /**
         * 每五秒检测一次是否交易成功
         */

        long t1=System.currentTimeMillis();
        while(true){
            receipt = web3j.ethGetTransactionReceipt(transactionHash).send().getTransactionReceipt();
            if(receipt.isPresent()) {
                System.out.println("交易生效！");
                break;
                }
            if((System.currentTimeMillis() - t0) > timeout) {
                System.out.println("超时！交易失败！");
                break;
                }
                Thread.sleep(1000*5);
            }
        long t2=System.currentTimeMillis();

        System.out.println("交易获取时长"+(t2-t1));

        /**
         * 取出blockNumber
         */




        List<Log> logs = receipt.get().getLogs();
        System.out.println(logs);
        Log logTransfer = logs.get(0);
        BigInteger blockNumber = logTransfer.getBlockNumber();
        map.put("blockNumber", blockNumber);


        /**
         * 取出日志相关内容
         */


        List<String> topics = logTransfer.getTopics();
        String _to = topics.get(2);
        String tokenId = topics.get(3);
        List<TypeReference<?>> NonIndexedParameters = Arrays.asList();
        List<TypeReference<?>> IndexedParameters = Arrays.asList(new TypeReference<Address>() {
        }, new TypeReference<Address>() {
        }, new TypeReference<Uint256>() {
        });



        Event event = new Event("Transfer", IndexedParameters, NonIndexedParameters);

//                    for (int i = 0; i < IndexedParameters.size(); i++) {
//                        Type value = FunctionReturnDecoder.decodeIndexedValue(topics.get(i + 1), IndexedParameters.get(i));
//                        indexedValues.add(value);
//                    }
//                    IndexedValues=indexedValues;
        /**
         * ownerAddress
         */

        List<TypeReference<Type>> IndexedParameters_to = new ArrayList<>();
        IndexedParameters_to.add(event.getIndexedParameters().get(1));
        List<Type> ownerAddress_list = FunctionReturnDecoder.decode(_to, IndexedParameters_to);
        ownerAddress = (String) ownerAddress_list.get(0).getValue();
//                    indexedValues.add(ownerAddress);
        map.put("ownerAddress", ownerAddress);
        /**
         * TokenId
         */

        List<TypeReference<Type>> IndexedParameters_tokenId = new ArrayList<>();
        IndexedParameters_tokenId.add(event.getIndexedParameters().get(2));
        List<Type> tokenId_list = FunctionReturnDecoder.decode(tokenId, IndexedParameters_tokenId);
        TokenID = String.valueOf((BigInteger) tokenId_list.get(0).getValue());
//                    indexedValues.add(TokenID);
        map.put("TokenID", TokenID);
//                    transferData.setOwnerAddress((String) map.get("ownerAddress"));
//                    transferData.setTokenId((String) map.get("TokenID"));
//                    System.out.println(map.get("TokenID"));

        System.out.println("maporiginal"+map);
        return map;
    }

}


