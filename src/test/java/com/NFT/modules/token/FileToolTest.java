package com.NFT.modules.token;

import com.NFT.modules.Utils.fileUpload.FileTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileToolTest {

    FileInputStream fis;

    {
        try {
            fis = new FileInputStream("/Users/mac/Desktop/blockchain/tokens/lotsowave.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private FileTool fl;

    @Test
    public void uploadFile() throws IOException {
        fl.uploadFiles(fis.readAllBytes(), "/Users/mac/Desktop/blockchain/losto_tokens/", "2");
    }



}

