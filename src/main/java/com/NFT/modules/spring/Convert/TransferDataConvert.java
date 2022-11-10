package com.NFT.modules.spring.Convert;

import com.NFT.modules.losto_tokens.Data.TransferData;
import com.NFT.modules.spring.Facade.MintTokenFacade;
import com.NFT.modules.spring.Service.ERC721.Receipt.GetTransferInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Component
public class TransferDataConvert {

    @Autowired
    private GetTransferInfoService getTransferInfoService;

   @Autowired
   private MintTokenFacade mintTokenFacade;

    public TransferData TransferData(MultipartFile file, String tokenName, String contractAddress, String ownerAddress) throws InterruptedException, IOException, ExecutionException {

        Map facadeMap = mintTokenFacade.mintTokenFacade(file, tokenName, ownerAddress, contractAddress);
        TransferData transferData = new TransferData();
        System.out.println(transferData.getBlockNumber());
//        transferData.setTokenURI((String) facadeMap.get("tokenURI"));
        transferData.setOwnerAddress((String) facadeMap.get("ownerAddress"));
        transferData.setTokenID((String) facadeMap.get("TokenID"));
        transferData.setTime((Date) facadeMap.get("time"));
        transferData.setBlockNumber((BigInteger) facadeMap.get("blockNumber"));
//        transferData.setBlockNumber(BigInteger.valueOf(123));
//        System.out.println( map.get("TokenID"));
        System.out.println((Date) facadeMap.get("time"));
        return transferData;

    }
}

