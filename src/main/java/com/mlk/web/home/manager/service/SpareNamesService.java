package com.mlk.web.home.manager.service;

import com.mlk.web.home.manager.model.dto.SpareNamesQueryRequest;
import com.mlk.web.home.manager.model.page.PageRequest;
import com.mlk.web.home.manager.model.page.PageResponse;
import com.mlk.web.home.manager.model.po.SpareNames;

public interface SpareNamesService {
    SpareNames queryById(Integer cId);

    Boolean editNames(SpareNames names);

    Boolean addNames(SpareNames names);

    PageResponse<SpareNames> queryNames(PageRequest<SpareNamesQueryRequest> model);
}
