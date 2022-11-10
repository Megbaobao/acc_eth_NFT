package com.NFT.modules.spring.Controller;

import com.NFT.modules.spring.Convert.TransferDataConvert;
import com.NFT.modules.losto_tokens.Data.TransferData;
import com.NFT.modules.spring.Facade.MintTokenFacade;
import com.NFT.modules.spring.Service.ERC721.Transaction.MintTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("mint")
public class MintController {
    @Autowired
    private MintTokenService mintTokenService;

    @Autowired
    private MintTokenFacade mintTokenFacade;

    @Autowired
    private TransferDataConvert transferDataConvert;


    @RequestMapping("minttoken")
    @ResponseBody
    public TransferData mintToken(@RequestParam(value = "file", required =true)MultipartFile file, @RequestParam(value = "tokenName", required =true)String tokenName, @RequestParam(value = "ownerAddress", required =true)String ownerAddress, @RequestParam(value = "contractAddress", required =true)String contractAddress) throws IOException, InterruptedException, ExecutionException {
//        mintTokenFacade.mintTokenFacade(file, tokenName, rawOwnerAddress, contracAddress);

        return transferDataConvert.TransferData(file, tokenName, contractAddress,ownerAddress);
    }
}

