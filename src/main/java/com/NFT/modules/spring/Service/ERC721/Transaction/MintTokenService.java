package com.NFT.modules.spring.Service.ERC721.Transaction;

import com.NFT.modules.spring.Mapper.NFTmintMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.ipc.UnixIpcService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.NFT.modules.Utils.contractConstant.*;
@Service
public class MintTokenService {

    @Autowired
    private Web3j web3j;

    //    @Autowired
//    private Geth geth;
    Geth geth = Geth.build(new UnixIpcService("/Users/mac/eth_node/data/geth.ipc"));


    @Autowired
    private NFTmintMapper nfTmintMapper;



    /**
     * 铸造NFT， 返回交易哈希值
     */

    public String mintTokenService(String contractAddress, String rawOwnerAddress, String rawURI) throws IOException {

        String callingAddress = "0x0D915853E29A81B8B3203756F752d28F792DE62c";
        String to = contractAddress;
        String name = MINT_TOKEN;

        Address ownerAddress = new Address(rawOwnerAddress);
        Utf8String tokenuri = new Utf8String(rawURI);
        List<Type> inputParameters = Arrays.asList(ownerAddress, tokenuri);
        List<TypeReference<?>> outputParameters = Arrays.asList(new TypeReference<Uint>() {
        });

        Function function = new Function(name, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = new Transaction(callingAddress, null, null, null, contractAddress, null, data);
        EthSendTransaction result = geth.ethSendTransaction(transaction).send();

        if (result.hasError()) {
            System.out.println("invoke fail");
            result.getError().getMessage();
        } else {
            System.out.println("invoke OK");
        }
        return result.getTransactionHash();
    }

}
