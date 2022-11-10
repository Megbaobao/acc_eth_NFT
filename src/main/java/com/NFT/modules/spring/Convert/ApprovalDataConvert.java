package com.NFT.modules.spring.Convert;

import com.NFT.modules.losto_tokens.Data.ApprovalData;
import com.NFT.modules.spring.Service.ERC721.Receipt.GetApprovalInfoService;
import com.NFT.modules.spring.Service.ERC721.Transaction.TokenApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class ApprovalDataConvert {

    @Autowired
    private GetApprovalInfoService getApprovalInfoService;

    @Autowired
    private TokenApprovalService tokenApprovalService;

    public ApprovalData ApprovalData(String contractAddress, String ownerAddress, String toAddress, Integer rawTokenId) throws InterruptedException, ExecutionException, IOException {
//        tokenApprovalService.TokenApproval(ownerAddress, toAddress, rawTokenId);
        Future<Map> futureMap = getApprovalInfoService.GetFilterChanges(contractAddress);
        tokenApprovalService.TokenApproval(ownerAddress, toAddress, rawTokenId,contractAddress);
        Map map = futureMap.get();
        ApprovalData approvalData = new ApprovalData();
        approvalData.setOwnerAddress((String) map.get("ownerAddress"));
        approvalData.setApprovedAddress((String) map.get("approvedAddress"));
        approvalData.setTokenId((String) map.get("TokenID"));

        return approvalData;
    }
}

