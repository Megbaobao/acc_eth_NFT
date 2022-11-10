package com.NFT.modules.spring.Service.ERC721.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
public class TokenTransferFromService {

    /**
     *
     * @param  ownerAddress The current owner of the NFT
     * @param  toAddress The new owner
     * @param  rawTokenId The NFT to transfer
     */


    @Autowired
    private Web3j web3j;

    @Autowired
    private Geth geth;

    @Async
    public String TokenTransferFrom(String contractAddress, String ownerAddress, String toAddress, Integer rawTokenId, String approvedAddress) throws IOException {


//        boolean addressExist = from == null;
//        if(addressExist){
//            from=MAIN_ADDRESS;
//        }

//        String from = MAIN_ADDRESS;


        String from=approvedAddress==null ? ownerAddress :approvedAddress;


        String to=contractAddress;

        String functionName ="transferFrom";


        Address _to = new Address(toAddress);
        Address _from= new Address(ownerAddress);
        List<TypeReference<?>> outputParameters = Arrays.asList();

//        BigInteger tokenId1 = new BigInteger(tokenId);
        Uint256 tokenId= new Uint256(rawTokenId);
        List<Type> inputParameters = Arrays.asList(_from, _to, tokenId);
        Function function = new Function(functionName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
//
//        EthCall response=web3j.ethCall(Transaction.createEthCallTransaction(from,contractAddress,data), DefaultBlockParameterName.LATEST).send();
//        System.out.println(response.getValue());
//        if(response.getValue().equals("0x"))
//            return false;
//        return true;

        Transaction transaction = new Transaction(ownerAddress, null, null, null, contractAddress, null, data);
        EthSendTransaction result = geth.ethSendTransaction(transaction).send();
        if(result.hasError()) {
            System.out.println("invoke fail");
            System.out.println(result.getError().getMessage());
            return result.getError().getMessage();
        } else {
            System.out.println("invoke OK");
            return result.getTransactionHash();
        }
    }
}

