package com.jakob.uer.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class UserInfo {

    @Id
    private String id;

    private String username;

    private String password;

    /**
     * 微信 openid
     */
    private String openid;

    /**
     * 1买家 2卖家
     */
    private Integer role;

    private Date createTime;

    private Date updateTime;
}
