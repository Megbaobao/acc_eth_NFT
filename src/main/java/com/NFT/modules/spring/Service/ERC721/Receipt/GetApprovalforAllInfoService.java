package com.NFT.modules.spring.Service.ERC721.Receipt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.geth.Geth;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.NFT.modules.Utils.contractConstant.*;

@Service
public class GetApprovalforAllInfoService {

    @Autowired
    private Web3j web3j;
    @Autowired
    private Geth geth;


    public String TokenTransfer(String ownerAddress, String toAddress, Long rawTokenId) throws IOException {
        String from = MAIN_ADDRESS;
        String to=CONTRACT_ADDRESS;
        String functionName = TRANSFER;

        Address _to = new Address(toAddress);
        Address _from= new Address(ownerAddress);
        List<TypeReference<?>> outputParameters = Arrays.asList(new TypeReference<Uint256>() {});

//        BigInteger tokenId1 = new BigInteger(tokenId);
        Uint256 tokenId= new Uint256(rawTokenId);
        List<Type> inputParameters = Arrays.asList(_from, _to, tokenId);
        Function function = new Function(functionName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);

        Transaction transaction = new Transaction(from, null, null, null, to, null, data);
        EthSendTransaction result = geth.ethSendTransaction(transaction).send();
        if(result.hasError()) {
            System.out.println("invoke fail");
            System.out.println(result.getError().getMessage());
        } else {
            System.out.println("invoke OK");
            System.out.println(result.getTransactionHash());
        }
        return result.getTransactionHash();
    }
}

