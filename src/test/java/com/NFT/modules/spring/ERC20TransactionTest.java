package com.NFT.modules.spring;


import com.NFT.modules.spring.Service.ERC20.Transaction.ERC20ApprovalService;
import com.NFT.modules.spring.Service.ERC20.Transaction.ERC20TokenTransferFromService;
import com.NFT.modules.spring.Service.ERC20.Transaction.ERC20TokenTransferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ERC20TransactionTest {
    @Autowired
    private ERC20TokenTransferFromService tokenTransferFromService;

    @Autowired
    private ERC20TokenTransferService erc20TokenTransferService;

    @Autowired
    private ERC20ApprovalService erc20ApprovalService;


    @Test
    public void TestTransferFrom() throws IOException {
        String b = tokenTransferFromService.TokenTransferFrom("0xF1F11B64DCbBDab9091366d3cf6Fdf55993295dd", "0x54c2ce87414a376a38e40b28ff73069541af206a", "0x0D915853E29A81B8B3203756F752d28F792DE62c","0xbafe9d02bd556d3bc91539bbc60603a01d18dddd", 5);
        System.out.println(b);
    }

    @Test
    public void TestTransfer() throws IOException {
        String b = erc20TokenTransferService.TokenTransfer("0xF1F11B64DCbBDab9091366d3cf6Fdf55993295dd","0x0D915853E29A81B8B3203756F752d28F792DE62c" , "0x54c2ce87414a376a38e40b28ff73069541af206a", 100);
        System.out.println(b);
    }

   @Test
    public void TestApproval() throws IOException {
       String b = erc20ApprovalService.TokenApproval("0x0D915853E29A81B8B3203756F752d28F792DE62c", "0x54c2ce87414a376a38e40b28ff73069541af206a", 600, "0xF1F11B64DCbBDab9091366d3cf6Fdf55993295dd");
       System.out.println(b);
   }


}

