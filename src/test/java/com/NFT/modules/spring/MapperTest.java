package com.NFT.modules.spring;


import com.NFT.modules.spring.Mapper.NFTmintMapper;
import com.NFT.modules.spring.Model.TokenmintModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MapperTest {

//    @Autowired
//    private ContractAddressMapper contractAddressMapper;
//
//    @Test
//    public void testSelectList() {
//        List<AddressModel> list = contractAddressMapper.selectList(null);

    @Autowired
    private NFTmintMapper nfTmintMapper;

    @Test
    public void testSelectList() {
            List<TokenmintModel> list1 = nfTmintMapper.selectList(null);


    }
}


