package com.NFT.modules.Utils.fileUpload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class FileTool {

     public static void uploadFiles(byte[] file, String filePath, String filename) throws IOException {
            File targetFile = new File(filePath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(filePath + filename);
            out.write(file);
            out.flush();
            out.close();
        }

}



