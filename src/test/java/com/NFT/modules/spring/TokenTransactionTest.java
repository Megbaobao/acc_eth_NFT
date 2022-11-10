package com.NFT.modules.spring;



import com.NFT.modules.losto_tokens.Data.TransferData;
import com.NFT.modules.spring.Service.Block.BlockTimeService;
import com.NFT.modules.spring.Service.ERC20.DeployContract.ERC20ContractService;
import com.NFT.modules.spring.Service.ERC721.DeployContract.ERC721contractService;
import com.NFT.modules.spring.Service.ERC721.Transaction.MintTokenService;
import com.NFT.modules.spring.Service.ERC721.Receipt.GetTransferInfoService;
import com.NFT.modules.spring.Service.ERC721.Transaction.TokenApprovalService;
import com.NFT.modules.spring.Service.ERC721.Transaction.TokenTransferFromService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.geth.Geth;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TokenTransactionTest {

    @Autowired
    private ERC721contractService erc721contractService;


    @Autowired
    private ERC20ContractService erc20ContractService;

    @Autowired
    private MintTokenService mintTokenService;

    @Autowired
    private TokenApprovalService tokenApprovalService;


    @Autowired
    private TokenTransferFromService tokenTransferFromService;

    @Autowired
    private Geth geth;


    @Autowired
    private  Web3j web3j;

    @Autowired
    private BlockTimeService blockTimeService;


    @Autowired
    private GetTransferInfoService getTransferInfoService;




    @Test
    public void TestERC721contractService() throws IOException, InterruptedException {
        String s = erc721contractService.deployContract();
        String contractAddress = erc721contractService.contractAddress();
    }

    @Test
    public void TestERC20contractService() throws IOException, InterruptedException {
        String s = erc20ContractService.deployContract();
        String contractAddress = erc20ContractService.contractAddress();
    }



    @Test
    public void TestMintTokenService() throws IOException {
        String hash = mintTokenService.mintTokenService("0x483aA3f32f4873F7aE2F7211dFFA6550B1E20EF9","0x0D915853E29A81B8B3203756F752d28F792DE62c", "hellomonkey");
        System.out.println(hash);
    }

    @Test
    public void TestTokenApproval() throws IOException {
        String hash = tokenApprovalService.TokenApproval("0x0D915853E29A81B8B3203756F752d28F792DE62c", "0x54c2ce87414a376a38e40b28ff73069541af206a", 8,"");
        System.out.println(hash);
    }

//    @Test
//    public void TestSafaTransferFrom() throws IOException {
//        Boolean bool = tokenTransferFromService.TokenTransferFrom("0x0d123B8D9cd5497abD0bfFB397FC3ff8e4f725d5", "0x0D915853E29A81B8B3203756F752d28F792DE62c", "0xbafe9d02bd556d3bc91539bbc60603a01d18dddd", 3);
//        System.out.println("bool"+bool);
//    }




    @Test
    public void TestGetBlockNumber(){
        geth.ethGetTransactionReceipt("0xa03cb25070a0ccf55de2879fca725d7c1aa75bc0e6b99a8f710a5768aa955288").observable().subscribe(receipt -> {
            Optional<TransactionReceipt> optional = receipt.getTransactionReceipt();
            if (optional.isPresent()) {
                BigInteger blockNumber = optional.get().getBlockNumber();
                System.out.println("present");
                System.out.println(blockNumber);
            }
        });
    }

    @Test
    public  void TestGetBlockTime(){
        System.out.println(blockTimeService.getBlockTime(BigInteger.valueOf(5604)));
    }

//    @Test
//    public void TestSafetransaction() throws IOException {
//          tokenTransferFromService.TokenTransferFrom("0xfbA21484b8b1BeAa00795827B21F25eBC65Dd786", "0xbafe9d02bd556d3bc91539bbc60603a01d18dddd", "0x0D915853E29A81B8B3203756F752d28F792DE62c", 1);
//
//    }



    @Test
    public void TestGetTransactionReceipt() throws IOException, InterruptedException {
        HashMap<Object, Object> map = getTransferInfoService.receiptListening("0xd5151a14e3519cabafbf72836ab48410e7b2b705a4829326e09be41bd777be73");
        System.out.println(map);
    }


    @Test
    public void TestAsyncGetTransactionReceipt() throws ExecutionException, InterruptedException, IOException {
//        CompletableFuture<EthGetTransactionReceipt> receipt = web3j.ethGetTransactionReceipt("0x0cd30bf49d4737be4d8a75ffed9421a7dd964b896e7144d6773cf0f823ad60fd").sendAsync();
////        receipt.thenApply(monkey->{
////            //BlockingQueue
////            // receipt.get().getTransactionReceipt().get().getTransactionHash();
////            return null;
////        });
//        System.out.println(receipt.join().getTransactionReceipt());

        Queue<EthGetTransactionReceipt> blockingQueue =  new LinkedList<>();
        EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt("0x0cd30bf49d4737be4d8a75ffed9421a7dd964b896e7144d6773cf0f823ad60fd").send();
        blockingQueue.add(receipt);
        EthGetTransactionReceipt receiptFromQueue = blockingQueue.poll();
        System.out.println(receiptFromQueue.getTransactionReceipt().get().getLogs());

    }


    @Test
    public void TestConvertData(){
        TransferData transferData = new TransferData();
        System.out.println(transferData.getBlockNumber());
    }

//    @Test
//    public void testTransferWithPrivateKey() throws IOException, CipherException {
//        String privateKey = "f33e52a149ce37c6303c50a8c665a7c7cc2a7493caddc51bec252c728580007f";
//        Credentials credentials = Credentials.create(privateKey);
//
//        String from = credentials.getAddress();
//        BigInteger nonce = getTransactionCount(from);
//        BigInteger gasPrice = new BigInteger("10000");
//        BigInteger gasLimit = new BigInteger("21000");
//        String to = "0x04e625099e059722a5983d717612c17cd3ff0f53";
//        BigInteger value = new BigInteger("180");
//        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, to, value);
//
//        byte[] signData = TransactionEncoder.signMessage(rawTransaction, credentials);
//        String signedTransactionData = Hex.toHexString(signData);
//
//        geth.ethSendRawTransaction("0x" + signedTransactionData).observable().subscribe(result -> {
//            if(result.hasError()) {
//                System.out.println(result.getRawResponse());
//            } else {
//                System.out.println(result.getTransactionHash());
//            }
//        });
//    }

}




