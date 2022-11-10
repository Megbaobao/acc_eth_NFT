package com.NFT.modules.spring.Model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("NFTmint")
public class TokenmintModel {

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
     *  调用合约账户
     */
    @TableField("callingAddress")
    private String callingAddress ;

    /**
     *  被调用合约
     */

    @TableField("contractAddress")
    private String contractAddress ;

    /**
     *  代笔持有者账户
     */

    @TableField("ownerAddress")
    private String ownerAddress ;

    /**
     *  调用函数名称
     */

    @TableField("functionName")
    private String functionName ;

    /**
     * uri
     */

    @TableField("uri")
    private String uri ;


}

