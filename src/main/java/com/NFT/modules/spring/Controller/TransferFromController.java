package com.NFT.modules.spring.Controller;

import com.NFT.modules.spring.Service.ERC721.Transaction.TokenTransferFromService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("transfer")
@ResponseBody
public class TransferFromController {
    @Autowired
    private TokenTransferFromService tokenTransferFromService;
    @RequestMapping("safeTransferFrom")
    public Boolean TransferFromController(@RequestParam(value = "contractAddress", required = true) String contractAddress, @RequestParam(value = "ownerAddress", required = true) String ownerAddress,
                                  @RequestParam(value = "toAddress", required = true) String toAddress, @RequestParam(value="rawTokenId", required = true) Integer rawTokenId,
                                          @RequestParam(value="approvedAddress", required = false) String approvedAddress) throws IOException {
           tokenTransferFromService.TokenTransferFrom(contractAddress,ownerAddress, toAddress,rawTokenId,null);
           return true;

    }
}

