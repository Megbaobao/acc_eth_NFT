package com.NFT.modules.spring.Service.Block;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigInteger;
import java.util.Date;
@Component
public class BlockTimeService {

    @Autowired
    private Web3j web3j;
    private Date blockTime;

    public Date getBlockTime(BigInteger blockNumber)  {
        if (this.blockTime == null) {
            try {
                DefaultBlockParameter blockParameter=new DefaultBlockParameterNumber(blockNumber);
                Request<?, EthBlock> ethBlockRequest = web3j.ethGetBlockByNumber(blockParameter, true);
                EthBlock blockRequest = ethBlockRequest.send();
                this.blockTime = new Date(blockRequest.getBlock().getTimestamp().longValue() * 1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
       return this.blockTime;
    }

}

