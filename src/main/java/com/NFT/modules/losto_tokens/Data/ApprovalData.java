package com.NFT.modules.losto_tokens.Data;

import lombok.Data;

import java.util.Date;
@Data
public class ApprovalData {

    /**
     *  ownerAddress     代币持有账户
     *  approvedAddress    被授权账户
     *  tokenId   代币Id
     */

    public String ownerAddress;

    public String approvedAddress;

    public String tokenId;

}

