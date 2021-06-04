package com.mlk.web.home.manager.model.dto;

import lombok.Data;

import java.io.Serializable;


/**
 * @author malikai
 * @date 2021年05月21日 16:35
 */
@Data
public class SpareNamesQueryRequest implements Serializable {

    private static final long serialVersionUID = 3757688278032437387L;
    private String name;

    private String type;

    private String memo;

    private String isShow;

    private Integer familyId;
}
