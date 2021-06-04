package com.mlk.web.home.manager.service;


import com.mlk.web.home.manager.model.po.ManagerFamilyGroup;
import com.mlk.web.home.manager.model.po.ManagerLogin;

import java.util.List;

public interface ManagerBaseService {
    ManagerLogin queryByLoginName(String name);

    List<ManagerFamilyGroup> queryByLoginId(Long loginId);

    Integer register(ManagerLogin model);

    Boolean binding(ManagerFamilyGroup group);

    Boolean cancelBinding(Long id);

    ManagerLogin login(ManagerLogin model);
}
