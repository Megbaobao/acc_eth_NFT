package com.NFT.modules.spring.Model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("contractAddressERC20")
public class AddressModelERC20 {

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
