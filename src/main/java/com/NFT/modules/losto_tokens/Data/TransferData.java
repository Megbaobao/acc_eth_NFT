package com.NFT.modules.losto_tokens.Data;


import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
//@Component
public class TransferData {


    public String ownerAddress;

    public String tokenID;

    public String tokenURI;

    public Date time;

    public BigInteger blockNumber;
}
