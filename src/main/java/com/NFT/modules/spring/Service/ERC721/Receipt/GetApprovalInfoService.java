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
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.NFT.modules.Utils.contractConstant.*;

@Service
public class GetApprovalInfoService {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Geth geth;

    private BigInteger filterId;
    private String ownerAddress;
    private String approvedAddress;
    private String TokenID;

    public BigInteger testNewFilter(String contractAddress){
        System.out.println(contractAddress);
        DefaultBlockParameter fromBlock = DefaultBlockParameter.valueOf(new BigInteger("2000"));
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

    @Async
    public Future<Map> GetFilterChanges(String contractAddress) throws InterruptedException {
        HashMap<Object, Object> map = new HashMap<>();
//        List<String> indexedValues = new ArrayList<>();
        BigInteger newFilterID=testNewFilter(contractAddress);
        System.out.println(newFilterID);

        Thread.sleep(20000);
        geth.ethGetFilterChanges(newFilterID).observable().subscribe(result -> {
            if(result.hasError()) {
                System.out.println(result.getError().getMessage());
            } else {
                System.out.println("listening starts ");
                List<EthLog.LogResult> logList = result.getLogs();
                System.out.println("logList"+logList);
                if (logList.isEmpty()){

                }else{
                    EthLog.LogObject Log = (EthLog.LogObject) result.getLogs().get(0);
                    System.out.println(Log);
                    List<String> topics = Log.getTopics();
                    String _from = topics.get(1);
                    String _to=topics.get(2);
                    String tokenId=topics.get(3);

                    List<TypeReference<?>> NonIndexedParameters = Arrays.asList();
                    List<TypeReference<?>> IndexedParameters = Arrays.asList(new TypeReference<Address>() {
                    }, new TypeReference<Address>(){},new TypeReference<Uint256>() {});
                    Event event = new Event("Approval", IndexedParameters, NonIndexedParameters);


//                    for (int i = 0; i < IndexedParameters.size(); i++) {
//                        Type value = FunctionReturnDecoder.decodeIndexedValue(topics.get(i + 1), IndexedParameters.get(i));
//                        indexedValues.add(value);
//                    }
//                    IndexedValues=indexedValues;

                    /**
                     * ownerAddress
                     */

                    List<TypeReference<Type>> IndexedParameters_from = new ArrayList<>();
                    IndexedParameters_from.add(event.getIndexedParameters().get(0));
                    List<Type> ownerAddress_list = FunctionReturnDecoder.decode(_from, IndexedParameters_from);
                    ownerAddress= (String) ownerAddress_list.get(0).getValue();
//                    indexedValues.add(ownerAddress);
                    map.put("ownerAddress", ownerAddress);

                    /**
                     * approvedAddress
                     */

                    List<TypeReference<Type>> IndexedParameters_to = new ArrayList<>();
                    IndexedParameters_to.add(event.getIndexedParameters().get(1));
                    List<Type> approvedAddress_list = FunctionReturnDecoder.decode(_to, IndexedParameters_to);
                    approvedAddress= (String) approvedAddress_list.get(0).getValue();
//                    indexedValues.add(approvedAddress);
                    map.put("approvedAddress", approvedAddress);

                    /**
                     * tokenId
                     */

                    List<TypeReference<Type>> IndexedParameters_tokenId = new ArrayList<>();
                    IndexedParameters_tokenId.add(event.getIndexedParameters().get(2));
                    List<Type> tokenId_list = FunctionReturnDecoder.decode(tokenId, IndexedParameters_tokenId);
                    TokenID= String.valueOf((BigInteger) tokenId_list.get(0).getValue());
//                    indexedValues.add(TokenID);
                    map.put("TokenID", TokenID);

                }
            }
        });
        return  new AsyncResult<Map>(map);
    }

}

