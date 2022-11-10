package com.NFT.modules.spring.Service.ERC721.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.geth.Geth;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.NFT.modules.Utils.contractConstant.TOTAL_SUPPLY;

@Service
public class TotalNFTOnContract {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Geth geth;


    public String totalNFTOnContract(String contractAddress) throws IOException, ExecutionException, InterruptedException {

        String callingAddress = "0x0D915853E29A81B8B3203756F752d28F792DE62c";
        String to = contractAddress;
        String name = TOTAL_SUPPLY;


        List<Type> inputParameters = Arrays.asList();
        List<TypeReference<?>> outputParameters = Arrays.asList(new TypeReference<Uint256>() {
        });

        Function function = new Function(name, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);

        EthCall response = web3j.ethCall(
                        Transaction.createEthCallTransaction(callingAddress, contractAddress, data),
                        DefaultBlockParameterName.LATEST)
                .send();

        if(response.hasError()){
            return response.getError().getMessage();}
        else{
            List<Type> result = FunctionReturnDecoder.decode(response.getResult(),function.getOutputParameters());
            BigInteger count = (BigInteger) result.get(0).getValue();
            return String.valueOf(count);
        }
    }
}




//
//        Transaction transaction = new Transaction(callingAddress, null, null, null, contractAddress, null, data);
//        EthSendTransaction result = geth.ethSendTransaction(transaction).send();
//
//        if (result.hasError()) {
//            System.out.println("invoke fail");
//            result.getError().getMessage();
//        } else {
//            System.out.println("invoke OK");
//        }
//        return result.getTransactionHash();
//    }

