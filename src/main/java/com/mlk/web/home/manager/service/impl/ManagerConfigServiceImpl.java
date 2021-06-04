package com.mlk.web.home.manager.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.mlk.util.invoke.ClsHttpMethod;
import com.mlk.util.invoke.ClsHttpRequest;
import com.mlk.util.invoke.model.DataResult;
import com.mlk.util.invoke.utils.JsonUtil;
import com.mlk.web.home.manager.model.page.PageRequest;
import com.mlk.web.home.manager.model.page.PageResponse;
import com.mlk.web.home.manager.model.po.ManagerConfig;
import com.mlk.web.home.manager.service.ManagerConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author malikai
 * @date 2021年05月31日 11:41
 */
@Service
public class ManagerConfigServiceImpl implements ManagerConfigService {

    @Value("${soa.home.manager.host:127.0.0.1:8081}")
    private String soaHomeManagerHost;

    @Override
    public PageResponse<ManagerConfig> queryManagerConfigByPage(PageRequest pageRequest) {
        String url = String.format("%s/manager/config/queryByPage", soaHomeManagerHost);

        return ClsHttpRequest.builder()
                .httpUrl(url)
                .method(ClsHttpMethod.POST)
                .body(JsonUtil.object2JSON(pageRequest))
                .build()
                .execute2Reference(new TypeReference<DataResult<PageResponse<ManagerConfig>>>() {
                });
    }

    @Override
    public ManagerConfig queryConfigByKey(String key) {

        String url = String.format("%s/manager/config/getById?configKey=%s", soaHomeManagerHost, key);
        return ClsHttpRequest.builder()
                .httpUrl(url)
                .method(ClsHttpMethod.GET)
                .build()
                .execute2Reference(new TypeReference<DataResult<ManagerConfig>>() {
                });
    }
}
