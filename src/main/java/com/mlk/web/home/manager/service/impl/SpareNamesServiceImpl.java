package com.mlk.web.home.manager.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.mlk.util.invoke.ClsHttpMethod;
import com.mlk.util.invoke.ClsHttpRequest;
import com.mlk.util.invoke.model.DataResult;
import com.mlk.util.invoke.utils.JsonUtil;
import com.mlk.web.home.manager.model.dto.SpareNamesQueryRequest;
import com.mlk.web.home.manager.model.page.PageRequest;
import com.mlk.web.home.manager.model.page.PageResponse;
import com.mlk.web.home.manager.model.po.SpareNames;
import com.mlk.web.home.manager.service.SpareNamesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author malikai
 * @date 2021年06月02日 13:41
 */
@Service
public class SpareNamesServiceImpl implements SpareNamesService {

    @Value("${soa.home.manager.host:127.0.0.1:8081}")
    private String soaHomeManagerHost;


    @Override
    public SpareNames queryById(Integer cId) {
        String url = String.format("%s/spareNames/getById?id=%s", soaHomeManagerHost, cId);
        return ClsHttpRequest.builder()
                .httpUrl(url)
                .method(ClsHttpMethod.GET)
                .build()
                .execute2Reference(new TypeReference<DataResult<SpareNames>>() {
                });
    }

    @Override
    public Boolean editNames(SpareNames names) {
        String url = String.format("%s/spareNames/update", soaHomeManagerHost);

        return ClsHttpRequest.builder()
                .httpUrl(url)
                .method(ClsHttpMethod.POST)
                .body(JsonUtil.object2JSON(names))
                .build()
                .execute2Reference(new TypeReference<DataResult<Boolean>>() {
                });
    }

    @Override
    public Boolean addNames(SpareNames names) {
        String url = String.format("%s/spareNames/add", soaHomeManagerHost);

        return ClsHttpRequest.builder()
                .httpUrl(url)
                .method(ClsHttpMethod.POST)
                .body(JsonUtil.object2JSON(names))
                .build()
                .execute2Reference(new TypeReference<DataResult<Boolean>>() {
                });
    }

    @Override
    public PageResponse<SpareNames> queryNames(PageRequest<SpareNamesQueryRequest> pageRequest) {
        String url = String.format("%s/spareNames/queryByPage", soaHomeManagerHost);

        return ClsHttpRequest.builder()
                .httpUrl(url)
                .method(ClsHttpMethod.POST)
                .body(JsonUtil.object2JSON(pageRequest))
                .build()
                .execute2Reference(new TypeReference<DataResult<PageResponse<SpareNames>>>() {
                });
    }
}
