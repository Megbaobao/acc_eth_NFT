package com.NFT.modules.spring.Service.ERC721.Receipt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.geth.Geth;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Service
public class GetTokenIdInfoService {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Geth geth;
    private BigInteger filterId;
    private BigInteger tokenIDecoded;
    @Async
    public BigInteger testNewFilter(String contractAddress){
        DefaultBlockParameter fromBlock = DefaultBlockParameter.valueOf(new BigInteger("1"));
        EthFilter ethFilter=new EthFilter(fromBlock, null, contractAddress);
        geth.ethNewFilter(ethFilter).observable().subscribe(result -> {
            if(result.hasError()) {
                System.out.println(result.getError().getMessage());
            } else {
                System.out.println(result.getFilterId());
                filterId=result.getFilterId();

            }
        });
        return filterId;
    }


    public BigInteger GetFilterChanges(String contractAddress) throws InterruptedException {
        BigInteger newFilterID=testNewFilter(contractAddress);
        Thread.sleep(20000);
        geth.ethGetFilterChanges(newFilterID).observable().subscribe(result -> {
            if(result.hasError()) {
                System.out.println(result.getError().getMessage());
            } else {
                List<EthLog.LogResult> logList = result.getLogs();
                System.out.println(logList);
                if (logList.isEmpty()){
                    System.out.println("No event emitted");
                }else{
                    EthLog.LogObject Log = (EthLog.LogObject) result.getLogs().get(1);
                    System.out.println(Log);
                    List<String> topics = Log.getTopics();
                    String tokenID = topics.get(1);
                    List<TypeReference<?>> NonIndexedParameters = Arrays.asList();
                    List<TypeReference<?>> IndexedParameters = Arrays.asList(new TypeReference<Uint256>() {
                    });
                    Event event = new Event("getTokenId", IndexedParameters, NonIndexedParameters);
                    List<Type> list = FunctionReturnDecoder.decode(tokenID, event.getIndexedParameters());
                    System.out.println(list);
                    tokenIDecoded= (BigInteger) list.get(0).getValue();
                }
            }
        });
        return tokenIDecoded;
    }

}
