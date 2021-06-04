package com.mlk.web.home.manager.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author malikai
 * @date 2021-5-21 16:32
 */
@Data
public class SpareNames implements Serializable {

    private static final long serialVersionUID = 8725120848408293313L;
    private Integer id;

    private String name;

    private String type;

    private String memo;

    private String isShow;

    private Date createTime;

    private Date updateTime;

    private Integer familyId;


}