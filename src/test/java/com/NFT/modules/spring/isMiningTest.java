package com.NFT.modules.spring;

import com.NFT.modules.spring.Service.Account.Mining;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class isMiningTest {

    @Autowired
    private Mining mining;

    @Test
    public void isMiningTest() throws IOException {
        System.out.println(mining.isMining());
    }

    @Test
    public void stopMiningTest() throws Exception {
        System.out.println(mining.minerStop());
    }

}
