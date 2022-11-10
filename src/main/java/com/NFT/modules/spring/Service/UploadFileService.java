package com.NFT.modules.spring.Service;

import com.NFT.modules.Utils.fileUpload.FileTool;

import com.NFT.modules.Utils.fileUpload.PreReadUploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {



   @Autowired(required = false)
   private PreReadUploadConfig preReadUploadConfig;


    //https://www.educative.io/answers/what-is-objectutilsisempty-in-java
//    @Override
    public String fileUpload(MultipartFile file, String tokenName) {
        if (file == null) {
            return null;
        }

        //String fileName = file.getOriginalFilename();
        String fileName = tokenName + ".png";
//        String uploadpath = preReadUploadConfig.getUploadPath();
        String uploadpath ="/Users/mac/Downloads/ethereum-web3j-2/src/main/java/com/NFT/modules/losto_tokens/";
        try {
            // String filepath="/Users/mac/Downloads/ethereum-web3j-2/src/main/java/com/NFT/modules/losto_tokens/3";
            FileTool.uploadFiles(file.getBytes(), uploadpath, fileName);
        } catch (Exception e) {

        }

        String url = "/static/" + fileName;
        System.out.println(url);
        return "localhost:8082"+url ;

    }
}


