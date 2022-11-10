package com.NFT.modules.spring.Service.ERC20.Query;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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

@Service
public class GetBalanceService {
    @Autowired
    private Web3j web3j;

    @Autowired
    private Geth geth;


    public BigInteger getBalance(String ownerAddress, String contractAddress) throws IOException, InterruptedException {

        String functionName = "balanceOf";

        Address _from= new Address(ownerAddress);
        List<Type> inputParameters = Arrays.asList(_from);
        List<TypeReference<?>> outputParameters = Arrays.asList(new TypeReference<Uint256>() {
        });

        Function function = new Function(functionName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);

        EthCall response=web3j.ethCall(Transaction.createEthCallTransaction(ownerAddress,contractAddress,data), DefaultBlockParameterName.LATEST).send();
        Thread.sleep(1000);
//        Transaction transaction = new Transaction(ownerAddress, null, null, null, contractAddress, null, data);
//        EthSendTransaction result = geth.ethSendTransaction(transaction).send();
        if (response.hasError()) {
            System.out.println("invoke fail");
            System.out.println(response.getError().getMessage());
            return new BigInteger(String.valueOf(0));
        } else {
            System.out.println("invoke OK");
            System.out.println("result " + response.getRawResponse());
            List<Type> result = FunctionReturnDecoder.decode(response.getResult(),function.getOutputParameters());
            System.out.println(result);
            BigInteger personalBalance = (BigInteger) result.get(0).getValue();
            return  personalBalance;

        }

    }
}
//    List<Type> result = FunctionReturnDecoder.decode(response.getResult(),function.getOutputParameters());
//    BigInteger totalSupply = (BigInteger) result.get(0).getValue();