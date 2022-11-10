package com.NFT.modules.spring;

import org.junit.Test;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class GetTokenTest {


//    @Autowired
//    private Web3j web3j;
//
//    @Autowired
//    private Geth geth;
//

    Web3jService web3jService = new HttpService("http://127.0.0.1:8545");
    Web3j web3j = Web3j.build(web3jService);

    Geth geth = Geth.build(new UnixIpcService("/Users/mac/eth_node/data/geth.ipc"));

    public static BigInteger filterId=null;


    public BigInteger testNewFilter(){
        DefaultBlockParameter fromBlock = DefaultBlockParameter.valueOf(new BigInteger("2330"));
        EthFilter ethFilter=new EthFilter(fromBlock, null, "0x18514FFBE6c646433CD1e929E2396f1E9163Ed72");
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


    @Test
    public void testGetFilterChanges() throws InterruptedException {
        BigInteger newFilterID=testNewFilter();
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
                System.out.println(list.get(0).getValue());
                }
            }
        });
    }




    @Test
    public void testEthGetLogs() {
        DefaultBlockParameter fromBlock = DefaultBlockParameter.valueOf(new BigInteger("t"));
        DefaultBlockParameter toBlock = DefaultBlockParameter.valueOf(new BigInteger("2340"));
        EthFilter ethFilter = new EthFilter( fromBlock, toBlock, "0x18514FFBE6c646433CD1e929E2396f1E9163Ed72");
        geth.ethGetLogs(ethFilter).observable().subscribe
                (result -> {
            if(result.hasError()) {
                System.out.println(result.getError().getMessage());
            } else {
                System.out.println();
                System.out.println("hello");
                List<TypeReference<?>> NonIndexedParameters = Arrays.asList();
                List<TypeReference<?>> IndexedParameters = Arrays.asList(new TypeReference<Uint256>() {
                });
                Event event = new Event("getTokenId", IndexedParameters, NonIndexedParameters);
                result.getLogs().forEach(log -> {
                    EthLog.LogObject obj = (EthLog.LogObject)log;

                    System.out.println("****************获取到新的event日志***********************");
                    System.out.println("日志所在的块：" + obj.getBlockNumber() + ", " + obj.getBlockHash());
                    System.out.println("日志所在的交易：" + obj.getTransactionHash());
                    System.out.println(obj.getTopics());
                    System.out.println(obj.getData());
                    String data = obj.getData();
                    List<Type> list = FunctionReturnDecoder.decode(data, event.getNonIndexedParameters());
                    System.out.println(list.get(0).getValue());
                });
            }
        });
    }

}



