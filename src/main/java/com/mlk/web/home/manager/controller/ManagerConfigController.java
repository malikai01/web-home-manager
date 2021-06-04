package com.mlk.web.home.manager.controller;

import com.mlk.util.invoke.model.DataResult;
import com.mlk.web.home.manager.annotation.NeedAuthority;
import com.mlk.web.home.manager.model.po.ManagerConfig;
import com.mlk.web.home.manager.service.ManagerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author malikai
 * @date 2021-5-31 14:47
 */
@Controller
@RequestMapping("v1/config")
public class ManagerConfigController {
    @Autowired
    private ManagerConfigService managerConfigService;

    @RequestMapping("/html")
    public String html() {
        return "config/config_list";
    }

    @RequestMapping(value = "/getByKey", method = RequestMethod.GET)
    @ResponseBody
    public DataResult<ManagerConfig> queryConfigByKey(@RequestParam String key) {
        return DataResult.ok(managerConfigService.queryConfigByKey(key));
    }


}
