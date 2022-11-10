package com.NFT.modules.spring.Service.Account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.ipc.UnixIpcService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.NFT.modules.Utils.contractConstant.BALANCE_OF;

@Service
@Slf4j
public class AccountService {


    Web3jService web3jService = new HttpService("http://127.0.0.1:8545");
    Web3j web3j = Web3j.build(web3jService);

    Geth geth = Geth.build(new UnixIpcService("/Users/mac/eth_node/data/geth.ipc"));


    /**
     * 查询账户列表
     */

    public List<String> getAccounts() throws IOException {
        List<String> accounts = web3j.ethAccounts().send().getAccounts();
        System.out.println(accounts);
        return accounts;
    }

    /**
     * 创建账户
     */

    public String createAccounts() throws IOException {
        String accountAddress = geth.personalNewAccount("123").send().getAccountId();
        return accountAddress;
    }

    /**
     * 查询账户余额
     *
     * @return
     */

    public BigInteger getBalance(String accountAddress) throws IOException {
        String address = accountAddress;
        BigInteger blockNumber = BigInteger.valueOf(6L);
        DefaultBlockParameter block1 = DefaultBlockParameter.valueOf(blockNumber);
        DefaultBlockParameter block2 = DefaultBlockParameter.valueOf("latest");
        EthGetBalance balance = web3j.ethGetBalance(address, block2).send();
        return balance.getBalance();
    }


    /**
     * 解锁账户
     *
     * @return
     */
    public Boolean unlockAccount(String contractAddress, String passphrase) {
        final Boolean[] unlockAccountResult = {null};
        BigInteger duration = new BigInteger("9000");
        geth.personalUnlockAccount(contractAddress, passphrase, duration).observable().subscribe(result -> {
            if (result.hasError()) {
                System.out.println(result.getError().getMessage());
            } else {
                System.out.println(result.getResult());
                unlockAccountResult[0] = result.getResult();
            }
        });

        return unlockAccountResult[0];
    }

    /**
     * 查看账户中的代币数量
     *
     * @param ownerAddress
     * @param contractAddress
     * @return
     */
    public BigInteger numberOfNFTS(String ownerAddress, String contractAddress) throws IOException {

        String functionName = BALANCE_OF;

        Address _from = new Address(ownerAddress);
        List<Type> inputParameters = Arrays.asList(_from);



        List<TypeReference<?>> outputParameters = Arrays.asList(new TypeReference<Uint256>() {
        });
        Function function = new Function(functionName, inputParameters, outputParameters);

        String data = FunctionEncoder.encode(function);

        EthCall response=web3j.ethCall(Transaction.createEthCallTransaction(ownerAddress,contractAddress,data), DefaultBlockParameterName.LATEST).send();

//        Transaction transaction = new Transaction(ownerAddress, null, null, null, contractAddress, null, data);
//        EthSendTransaction result = geth.ethSendTransaction(transaction).send();
//        if (result.hasError()) {
//            System.out.println("invoke fail");
//            System.out.println(result.getError().getMessage());
//        } else {
//            System.out.println("invoke OK");
//            System.out.println("result " + result.getResult());
//            System.out.println("hash " + result.getTransactionHash());
//        }

        List<Type> result = FunctionReturnDecoder.decode(response.getResult(),function.getOutputParameters());
        BigInteger balance = (BigInteger) result.get(0).getValue();
        return balance;
    }

//    public BigInteger getReturnValue(String transactionHash) throws IOException {
//        geth.ethGetTransactionReceipt(transactionHash).observable().subscribe(result -> {
//            Optional<TransactionReceipt> optional = result.getTransactionReceipt();
//            if(optional.isPresent()) {
//                System.out.println(optional.get().);
//            }
//        });
//
//        return null;
//    }
//     public String ethTransfer(String fromAddress, String toAddress, int amount) throws IOException {
//         System.out.println(Convert.toWei(String.valueOf(amount), Convert.Unit.ETHER).toString());
//         BigInteger value = new BigInteger(Convert.toWei("5", Convert.Unit.ETHER).toString());
//         Transaction transaction = new Transaction(fromAddress, null, null, null, toAddress, value, null);
//
//
//         Observable<EthSendTransaction> test = geth.ethSendTransaction(transaction).observable();
//         EthSendTransaction res2 = new EthSendTransaction();
//         res2.getTransactionHash();
//         Subscription test2 = test.subscribe((Action1<? super EthSendTransaction>) res2);
//
//
//         geth.ethSendTransaction(transaction).observable().subscribe(res->{
//             if(res.hasError()){
//                 System.out.println(res.getError().getMessage());
//                 EthSendTransaction Observable;
//
//             }else{
//                 System.out.println(res.getTransactionHash());
//                 return res.getTransactionHash();
//             }
//         return res.hasError();}
//         );
//     }


//         String privateKey = "f33e52a149ce37c6303c50a8c665a7c7cc2a7493caddc51bec252c728580007f";
//         Credentials credentials = Credentials.create(privateKey);
//         EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(String.valueOf(fromAddress), DefaultBlockParameterName.LATEST).sendAsync().get();
//         BigInteger nonce=ethGetTransactionCount.getTransactionCount();
//         RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
//                 nonce, Convert.toWei("0", Convert.Unit.GWEI).toBigInteger(), Convert.toWei("", Convert.Unit.GWEI).toBigInteger(),toAddress,new BigInteger(String.valueOf(amount)));
//         byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
//         String hexValue = Numeric.toHexString(signedMessage);

//         EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
//         if(ethSendTransaction.hasError()){
//             System.out.println("transfer error:"+ethSendTransaction.getError().getMessage());
//             log.info("transfer error:", ethSendTransaction.getError().getMessage());
//             return ethSendTransaction.getError().getMessage();
//         }else {
//             String transactionHash = ethSendTransaction.getTransactionHash();
//             return transactionHash;
//         }
//     }
}



