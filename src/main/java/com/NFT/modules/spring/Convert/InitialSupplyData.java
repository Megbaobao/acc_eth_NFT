package com.NFT.modules.spring.Convert;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class InitialSupplyData {

    @Autowired
    private InitialSupplyData initialSupplyData;

    public InitialSupplyData InitialSupplyData(){

        return initialSupplyData;
    }
}

