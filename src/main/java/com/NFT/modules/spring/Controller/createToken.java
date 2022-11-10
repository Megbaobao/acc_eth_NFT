package com.NFT.modules.spring.Controller;

import com.NFT.modules.spring.Service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.geth.Geth;

import java.util.List;

@RestController
@RequestMapping("personal")
@ResponseBody
public class createToken {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Geth geth;

    @Autowired
    private UploadFileService ps;

  @PostMapping("/postfile")
  public String fileUpload(@RequestParam(value = "tokenImg", required = false) MultipartFile file,
                             @RequestParam(value = "picNo", required = false) Long userId) {
      String test = ps.fileUpload(file, String.valueOf(userId));
      System.out.println(test);
      return test;

    }

//    @PostMapping("/postfile")
//    public List fileUpload(@RequestParam(value = "tokenImg", required = false) MultipartFile file,
//                           @RequestParam(value = "picNo", required = false) Long userId)) {
//
//
//    }

}


