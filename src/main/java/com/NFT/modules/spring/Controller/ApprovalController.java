package com.NFT.modules.spring.Controller;

import com.NFT.modules.spring.Convert.ApprovalDataConvert;
import com.NFT.modules.losto_tokens.Data.ApprovalData;
import com.NFT.modules.spring.Service.ERC721.Transaction.TokenApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("approve")
public class ApprovalController {

    @Autowired
    private TokenApprovalService tokenApprovalService;
    @Autowired
    private ApprovalDataConvert approvalDataConvert;

    @RequestMapping("approval")
    @ResponseBody
    public ApprovalData Approval(@RequestParam(value = "ownerAddress", required =true)String ownerAddress, @RequestParam(value = "rawTokenId", required =true)Integer rawTokenId, @RequestParam(value = "contractAddress", required =true)String contractAddress, @RequestParam(value = "toAddress", required =true)String toAddress) throws InterruptedException, IOException, ExecutionException {
        return approvalDataConvert.ApprovalData(contractAddress, ownerAddress, toAddress, rawTokenId);
    }
}

