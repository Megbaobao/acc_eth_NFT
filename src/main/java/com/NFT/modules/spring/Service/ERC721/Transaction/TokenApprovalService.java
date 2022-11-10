package com.NFT.modules.spring.Service.ERC721.Transaction;


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

import static com.NFT.modules.Utils.contractConstant.APPRPVAL;
import static com.NFT.modules.Utils.contractConstant.CONTRACT_ADDRESS;

@Service
public class TokenApprovalService {

    @Autowired
    private Web3j web3j;
    @Autowired
    private Geth geth;


    public String TokenApproval(String ownerAddress, String approvedAddress, Integer rawTokenId, String contractAddress) throws IOException {
        /**
         * from: 调用合约的账户
         * to: 合约地址
         * functionName: 方法名称
         * _to: 被授权地址
         *  tokenId: tokenId
         */

        String from = ownerAddress;
//        String to="0x54416c40e5e67fc5f4f5ac9dc1693fa1635d7335";
        String functionName = "approve";
        Address _to = new Address(approvedAddress);
        Uint256 tokenId= new Uint256(rawTokenId);
        List<Type> inputParameters = Arrays.asList(_to, tokenId);

        List<TypeReference<?>> outputParameters = Arrays.asList();

        Function function = new Function(functionName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);

        Transaction transaction = new Transaction(from, null, null, null, contractAddress, null, data);
        EthSendTransaction result = geth.ethSendTransaction(transaction).send();
        if(result.hasError()) {
            System.out.println("invoke fail");
            return result.getError().getMessage();
        } else {
            System.out.println("invoke OK");
            return result.getTransactionHash();
        }

    }
}

