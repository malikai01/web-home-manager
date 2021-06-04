package com.mlk.web.home.manager.model.po;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author malikai
 * @date 2021-5-21 16:30
 */
@Data
public class ManagerLogin implements Serializable {

    private static final long serialVersionUID = -4715141520739907634L;
    private Integer id;

    private String loginName;

    private String password;

    private Date registerTime;

    private Date updateTime;

    private String isDelete;

    private String loginNickName;

    private String cardNumber;

    private Integer cardNumberAttachId;

    private Integer cardNumberAttachId2;

    private String phone;

}
