package com.NFT.modules.spring.Model;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;



@Data
@TableName("accountAddress")
public class AddressModel implements Serializable {


        /**
         * 主键
         */
        @TableId(value = "id", type = IdType.ASSIGN_ID)
        private String id;

        /**
         * 交易哈希值
         */
        @TableField("transactionHash")
        private String transactionHash;
        /**
         *
         */
        @TableField("contractAddress")
        private String contractAddress;

    }

