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
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.geth.Geth;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static com.NFT.modules.Utils.contractConstant.BALANCE_OF;

@Service
public class GetTotalSupplyService {
    @Autowired
    private Web3j web3j;

    @Autowired
    private Geth geth;


    public BigInteger getTotalSupply(String ownerAddress, String contractAddress) throws IOException, InterruptedException {

        Address _from = new Address(ownerAddress);

        String functionName ="totalSupply";
        List<Type> inputParameters = Arrays.asList();
        List<TypeReference<?>> outputParameters = Arrays.asList(new TypeReference<Uint256>() {
        });

        Function function = new Function(functionName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);


        EthCall response=web3j.ethCall(Transaction.createEthCallTransaction(ownerAddress,contractAddress,data), DefaultBlockParameterName.LATEST).send();
//        Transaction transaction = new Transaction(ownerAddress, null, null, null, contractAddress, null, data);
//        EthSendTransaction result = geth.ethSendTransaction(transaction).send();

        if (response.hasError()) {
            System.out.println("invoke fail");
            System.out.println(response.getError().getMessage());
            return new BigInteger(String.valueOf(0));
        } else {
            System.out.println("invoke OK");
            List<Type> result = FunctionReturnDecoder.decode(response.getResult(),function.getOutputParameters());
            BigInteger totalSupply = (BigInteger) result.get(0).getValue();
            return  totalSupply;

        }

    }
}


