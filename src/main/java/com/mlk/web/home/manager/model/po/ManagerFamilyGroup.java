package com.mlk.web.home.manager.model.po;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author malikai
 * @date 2021-5-21 16:30
 */
@Data
public class ManagerFamilyGroup implements Serializable{

    private static final long serialVersionUID = 2805970866842163944L;
    private Long id;

    private Long loginId;

    private String name;

    private Integer age;

    private Date createTime;

    private Date updateTime;

    private String relation;

    private String isDelete;

    private String gender;

    private String memo;
}
