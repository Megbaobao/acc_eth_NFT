package com.NFT.modules.spring.Facade;

import com.NFT.modules.spring.Service.Block.BlockTimeService;
import com.NFT.modules.spring.Service.ERC721.Receipt.GetTransferInfoService;
import com.NFT.modules.spring.Service.ERC721.Transaction.MintTokenService;
import com.NFT.modules.spring.Service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.geth.Geth;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

@Component
public class MintTokenFacade {

    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private MintTokenService mintTokenService;

    @Autowired
    private BlockTimeService blockTimeService;

    @Autowired
    private GetTransferInfoService getTransferInfoService;

    @Autowired
    private Geth geth;

    @Autowired
    private Web3j web3j;

    public Map mintTokenFacade(MultipartFile file, String tokenName, String ownerAddress, String contractAddress) throws IOException, InterruptedException {
        String  tokenURI= uploadFileService.fileUpload(file, tokenName);
        System.out.println(tokenURI);
        String hash = mintTokenService.mintTokenService(contractAddress, ownerAddress, tokenURI);
        System.out.println("hash"+hash);
        HashMap<Object, Object> mapInfo = getTransferInfoService.receiptListening(hash);
        if (mapInfo.isEmpty()){
            System.out.println("Transaction Log is empty");
        }
        if (mapInfo.get("blockNumber") == null) {
            System.out.println("blockNumber is null");
        }
        Date time = blockTimeService.getBlockTime((BigInteger) mapInfo.get("blockNumber"));
        mapInfo.put("time", time);
        return mapInfo;

//        System.out.println("hash"+hash);
//        geth.ethGetTransactionReceipt(mintTokenService.mintTokenService(contracAddress, ownerAddress, tokenURI)).observable().subscribe(receipt -> {
//            Optional<TransactionReceipt> optional = receipt.getTransactionReceipt();
//            if (optional.isPresent()) {
//                BigInteger blockNumber = optional.get().getBlockNumber();
//                System.out.println("blockNumber"+blockNumber);
//                Date time = blockTimeService.getBlockTime(blockNumber);
//                System.out.println("time"+time);
//                map.put("time",time);
//            }else {
//                System.out.println("optional is null");
//                System.out.println("hash");
//            }
//        });
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//        String now3 = df.format(System.currentTimeMillis());
////        map.put("tokenURI",tokenURI);
//        System.out.println(map)


    }
}

