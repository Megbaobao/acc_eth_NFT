package com.NFT.modules.spring.Controller;


import com.NFT.modules.spring.Service.ERC20.Query.GetTotalSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;

@RestController
@RequestMapping("/ERC20Enquire")
public class ERC20EnquireController {

    @Autowired
    private GetTotalSupplyService getTotalSupplyService;

    @RequestMapping("/TotalSupply")
    public BigInteger GetTotalSupply(@RequestParam(value="ownerAddress",required = true) String ownerAddress, @RequestParam(value="contractAddress", required = true)String contractAddress) throws IOException, InterruptedException {
        return getTotalSupplyService.getTotalSupply(ownerAddress,contractAddress);

    }
}
