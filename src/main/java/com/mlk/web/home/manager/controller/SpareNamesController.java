package com.mlk.web.home.manager.controller;

import com.mlk.util.invoke.model.DataResult;
import com.mlk.web.home.manager.UserContext;
import com.mlk.web.home.manager.annotation.NeedAuthority;
import com.mlk.web.home.manager.model.dto.SpareNamesQueryRequest;
import com.mlk.web.home.manager.model.page.PageRequest;
import com.mlk.web.home.manager.model.page.PageResponse;
import com.mlk.web.home.manager.model.po.ManagerLogin;
import com.mlk.web.home.manager.model.po.SpareNames;
import com.mlk.web.home.manager.service.SpareNamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 *
 * @author malikai
 * @date 2021-6-2 13:54
 */
@Controller
@RequestMapping("v1/spareNames")
public class SpareNamesController {
    @Autowired
    private SpareNamesService spareNamesService;

    @RequestMapping("/addHtml")
    public String addHtml() {
        return "spareNames/spareNames_add";
    }

    @RequestMapping("/queryHtml")
    public String queryHtml() {
        return "spareNames/spareNames_list";
    }

    @RequestMapping("/editHtml/{cId}")
    public String editHtml(@PathVariable Integer cId, HttpServletRequest request) {
        SpareNames spareNames = spareNamesService.queryById(cId);
        request.setAttribute("spareNames", spareNames);
        return "spareNames/spareNames_edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    @NeedAuthority
    public DataResult<Boolean> editNames(@RequestBody SpareNames names) {
        ManagerLogin login = UserContext.getInstance().getUser();
        names.setUpdateTime(new Date());
        names.setIsShow("1");
        names.setFamilyId(login.getId());
        return DataResult.ok("修改成功", spareNamesService.editNames(names));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @NeedAuthority
    public DataResult<Boolean> addNames(@RequestBody SpareNames names) {
        ManagerLogin login = UserContext.getInstance().getUser();
        names.setCreateTime(new Date());
        names.setUpdateTime(new Date());
        names.setIsShow("1");
        names.setFamilyId(login.getId());
        return DataResult.ok("修改成功", spareNamesService.addNames(names));
    }

    @RequestMapping(value = "/queryByPage", method = RequestMethod.POST)
    @ResponseBody
    @NeedAuthority
    public DataResult<PageResponse<SpareNames>> queryNames(PageRequest<SpareNamesQueryRequest> model) {
        ManagerLogin login = UserContext.getInstance().getUser();
        model.getParamData().setFamilyId(login.getId());
        return DataResult.ok(spareNamesService.queryNames(model));
    }
}
