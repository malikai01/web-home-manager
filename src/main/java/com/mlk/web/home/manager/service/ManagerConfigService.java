package com.mlk.web.home.manager.service;

import com.mlk.web.home.manager.model.page.PageRequest;
import com.mlk.web.home.manager.model.page.PageResponse;
import com.mlk.web.home.manager.model.po.ManagerConfig;

public interface ManagerConfigService {

    PageResponse<ManagerConfig> queryManagerConfigByPage(PageRequest pageRequest);

    ManagerConfig queryConfigByKey(String key);
}
