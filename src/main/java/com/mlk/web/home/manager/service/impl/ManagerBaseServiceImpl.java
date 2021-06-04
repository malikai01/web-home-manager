package com.mlk.web.home.manager.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.mlk.util.invoke.ClsHttpMethod;
import com.mlk.util.invoke.ClsHttpRequest;
import com.mlk.util.invoke.model.DataResult;
import com.mlk.util.invoke.utils.JsonUtil;
import com.mlk.web.home.manager.model.po.ManagerFamilyGroup;
import com.mlk.web.home.manager.model.po.ManagerLogin;
import com.mlk.web.home.manager.service.ManagerBaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author malikai
 * @date 2021年05月26日 11:03
 */
@Service
public class ManagerBaseServiceImpl implements ManagerBaseService {

    @Value("${soa.home.manager.host:127.0.0.1:8081}")
    private String soaManagerHost;

    @Override
    public ManagerLogin queryByLoginName(String name) {
        String url = String.format("%s/manager/user/queryByName?loginName=%s", soaManagerHost, name);
        return ClsHttpRequest.builder()
                .httpUrl(url)
                .method(ClsHttpMethod.GET)
                .build()
                .execute2Reference(new TypeReference<DataResult<ManagerLogin>>() {
                });
    }

    @Override
    public List<ManagerFamilyGroup> queryByLoginId(Long loginId) {
        String url = String.format("%s/manager/family/queryByLoginId?loginId=%d", soaManagerHost, loginId);
        return ClsHttpRequest.builder()
                .httpUrl(url)
                .method(ClsHttpMethod.GET)
                .build()
                .execute2Reference(new TypeReference<DataResult<List<ManagerFamilyGroup>>>() {
                });
    }

    @Override
    public Integer register(ManagerLogin model) {

        String url = String.format("%s/manager/user/add", soaManagerHost);
        return ClsHttpRequest.builder()
                .httpUrl(url)
                .method(ClsHttpMethod.POST)
                .body(JsonUtil.object2JSON(model))
                .build()
                .execute2Reference(new TypeReference<DataResult<Integer>>() {
                });
    }

    @Override
    public Boolean binding(ManagerFamilyGroup group) {
        String url = String.format("%s/manager/family/binding", soaManagerHost);
        return ClsHttpRequest.builder()
                .httpUrl(url)
                .method(ClsHttpMethod.POST)
                .body(JsonUtil.object2JSON(group))
                .build()
                .execute2Reference(new TypeReference<DataResult<Boolean>>() {
                });
    }

    @Override
    public Boolean cancelBinding(Long id) {
        String url = String.format("%s/manager/family/cancelBinding?loginId=%d", soaManagerHost, id);
        return ClsHttpRequest.builder()
                .httpUrl(url)
                .method(ClsHttpMethod.GET)
                .build()
                .execute2Reference(new TypeReference<DataResult<Boolean>>() {
                });
    }

    @Override
    public ManagerLogin login(ManagerLogin model) {
        String url = String.format("%s/manager/user/login?loginName=%s&password=%s", soaManagerHost, model.getLoginName(), model.getPassword());
        return ClsHttpRequest.builder()
                .httpUrl(url)
                .method(ClsHttpMethod.GET)
                .build()
                .execute2Reference(new TypeReference<DataResult<ManagerLogin>>() {
                });
    }
}
