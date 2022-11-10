package com.NFT.modules.spring.Controller;

import com.NFT.modules.spring.Service.Account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigInteger;

@Controller
@RequestMapping("account")
@ResponseBody
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * 解锁账户
     */

    @RequestMapping("unlockaccount")
    public Boolean unLockAccout(@RequestParam(value = "accountAddress", required =true)String accountAddress, @RequestParam(value = "passPhrase", required =true)String passPhrase){
        Boolean result = accountService.unlockAccount(accountAddress, passPhrase);
        return result;
    }

    /**
     * 个人数字藏品总数
     */

    @RequestMapping("NFTCount")
    public BigInteger numberOfNFT(@RequestParam(value="ownerAddress",required = true) String ownerAddress, @RequestParam(value="contractAddress", required = true) String contractAddress) throws IOException {
        return accountService.numberOfNFTS(ownerAddress,contractAddress);
    }

    /**
     * 查询以太币余额
     */

    @RequestMapping("ethBalance")
    public BigInteger ethBalance(@RequestParam(value="accountAddress",required = true)String accounAddress) throws IOException {
        return accountService.getBalance(accounAddress);
    }

    /**
     * 以太币转账
     */
//
//    @RequestMapping
//    public String ethTransfer(String fromAddress, String toAddress, int amount){
//        return accountService.ethTransfer(fromAddress, toAddress, amount);
//    }



}


