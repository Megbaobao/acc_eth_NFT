package com.NFT.modules.spring.Service.ERC20.DeployContract;

import com.NFT.modules.spring.Mapper.ContractAddressMapperERC20;
import com.NFT.modules.spring.Model.AddressModel;
import com.NFT.modules.spring.Model.AddressModelERC20;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.geth.Geth;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static com.NFT.modules.Utils.contractConstant.*;
import static com.NFT.modules.Utils.contractConstant.ID;

@Service
public class ERC20ContractService {
    @Autowired
    private Web3j web3j;

    @Autowired
    private Geth geth;


    @Autowired
    private ContractAddressMapperERC20 contractAddressMapperERC20;

    private String id;

    public String deployContract() throws IOException {

        String from = MAIN_ADDRESS;
        byte[] content = Files.readAllBytes(Paths.get(FILE_PATH_ERC20));
        String data = new String(content);
        BigInteger gasLimit = new BigInteger("13616930");

        Transaction transaction = new Transaction(from, null, null, gasLimit, null, null, data);

        EthEstimateGas estimateGas = geth.ethEstimateGas(transaction).send();
        if (estimateGas.hasError()) {
            System.out.println(estimateGas.getError().getMessage());
        } else {
            System.out.println(estimateGas.getAmountUsed());
        }


        EthSendTransaction result = geth.ethSendTransaction(transaction).send();
        if (result.hasError()) {
            System.out.println("deploy fail");
            System.out.println(result.getError().getMessage());
        } else {
            //0x99b1006efe19528d1e7e3dc8a534b0a7c1b4d62998295f239f6f2022730741c4
            System.out.println("deploy OK");
            System.out.println(result.getTransactionHash());
        }

        ID=id(result.getTransactionHash());

        return ID;
    }

    public String id(String transactionHash) {
        AddressModelERC20 addressModel = new AddressModelERC20();

        addressModel.setTransactionHash(transactionHash);
        int count = contractAddressMapperERC20.insert(addressModel);
//        if (count != 0) {
//            return addressModel.getId();
//        } else {
//            return "insert failed";
//        }
        return addressModel.getId();
    }

    public String contractAddress () throws InterruptedException {
        Thread.sleep(10000);
        String transactionHash=contractAddressMapperERC20.selectById(ID).getTransactionHash();
        System.out.println(transactionHash);
        geth.ethGetTransactionReceipt(transactionHash).observable().subscribe(result -> {
                    Optional<TransactionReceipt> optional = result.getTransactionReceipt();
                    if (optional.isPresent()) {
                        String contractAddress = optional.get().getContractAddress();
                        System.out.println("conttractAddress"+contractAddress);
                        AddressModelERC20 addressModel = new AddressModelERC20();
                        addressModel.setId(ID);
                        addressModel.setContractAddress(contractAddress);
                        contractAddressMapperERC20.updateById(addressModel);
                    } else {
                        String contractAddress = "contractAddress is Null";
                        System.out.println(contractAddress);
                    }
                }
        );

        return CONTRACT_ADDRESS;
    }

}
