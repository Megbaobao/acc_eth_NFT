//package com.NFT.modules.spring;
//
//import io.ipfs.api.MerkleNode;
//import io.ipfs.api.NamedStreamable;
//import io.ipfs.multihash.Multihash;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//public class IPFS {
//
//    io.ipfs.api.IPFS ipfs = new io.ipfs.api.IPFS("/ip4/127.0.0.1/tcp/5002");
//
//    public IPFS() throws IOException {
//    }
//
//
//    @Test
//    public void addFile() throws IOException {
//        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File("/Users/mac/Downloads/ethereum-web3j-2/src/test/java/com/NFT/modules/spring/hello.txt"));
//        MerkleNode addResult = ipfs.add(file).get(0);
//        System.out.println(addResult);
//    }
//
//    @Test
//    public void getFile() throws IOException {
//        Multihash filePointer = Multihash.fromBase58("Qmf6RRhQkyKeaGTsPSfXgTaz6DcrZWctzzyBoGbsrPPxD5");
//        byte[] fileContents = ipfs.cat(filePointer);
//        System.out.println(new String(fileContents));
//    }
//
//
//
//
//    //查看文件信息
//
//
//
//
//}
//
