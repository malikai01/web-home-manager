package com.mlk.web.home.manager.controller;

import com.mlk.util.invoke.model.DataResult;
import com.mlk.web.home.manager.UserContext;
import com.mlk.web.home.manager.annotation.NeedAuthority;
import com.mlk.web.home.manager.model.po.ManagerFamilyGroup;
import com.mlk.web.home.manager.model.po.ManagerLogin;
import com.mlk.web.home.manager.service.ManagerBaseService;
import com.mlk.web.home.manager.utils.MD5Util;
import com.mlk.web.home.manager.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author malikai
 * @date 2021-6-4 14:14
 */
@Controller
@RequestMapping("/manager")
@Api(description = "用户基本信息")
@Slf4j
public class ManagerBaseController {
    @Autowired
    private ManagerBaseService managerBaseService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "/manager/register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/manager/login";
    }

    @RequestMapping(value = "/register/nextStep/{loginId}", method = RequestMethod.GET)
    public String nextStep(@PathVariable Long loginId, HttpServletRequest req) {
        List<ManagerFamilyGroup> listBinding = managerBaseService.queryByLoginId(loginId);
        req.setAttribute("listBinding", listBinding);
        req.setAttribute("loginId", loginId);
        return "/manager/nextStep";
    }

    /**
     * firstStep
     *
     * @param model
     * @return com.mlk.util.invoke.model.DataResult<java.lang.Integer>
     * @author malikai
     * @date 2021-6-2 13:58
     */
    @RequestMapping(value = "/register/add", method = RequestMethod.POST)
    @ApiOperation(value = "注册")
    @ResponseBody
    public DataResult<Integer> add(@RequestBody ManagerLogin model) {
        ManagerLogin response = managerBaseService.queryByLoginName(model.getLoginName());
        if (response != null) {
            return DataResult.fail("用户名已存在！");
        }
        Integer loginId = managerBaseService.register(model);
        if (loginId > 0) {
            return DataResult.ok("注册成功！", loginId);
        } else {
            return DataResult.fail("注册失败！");
        }
    }

    @RequestMapping(value = "/register/binding", method = RequestMethod.POST)
    @ApiOperation(value = "绑定家庭成员")//secondStep
    @ResponseBody
    public DataResult<List<ManagerFamilyGroup>> binding(@RequestBody ManagerFamilyGroup group) {
        Boolean result = managerBaseService.binding(group);
        if (result) {
            List<ManagerFamilyGroup> list = managerBaseService.queryByLoginId(group.getLoginId());
            return DataResult.ok("绑定成功！", list);
        } else {
            return DataResult.fail("绑定失败！");
        }
    }

    @RequestMapping(value = "/register/cancelBinding/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "取消绑定家庭成员")
    @ResponseBody
    public DataResult cancelBinding(@PathVariable Long id) {
        Boolean result = managerBaseService.cancelBinding(id);
        if (result) {
            return DataResult.ok("取消绑定成功！");
        } else {
            return DataResult.fail("取消绑定失败！");
        }
    }

    @RequestMapping(value = "/toLogin", method = RequestMethod.POST)
    @ApiOperation(value = "登录")
    @ResponseBody
    public DataResult<ManagerLogin> login(@RequestBody ManagerLogin model, HttpServletResponse httpServletResponse, HttpServletRequest request) {
        model.setPassword(MD5Util.getMD5(model.getPassword()));
        ManagerLogin response = managerBaseService.login(model);
        if (response != null) {
            String token = TokenUtils.createJwtToken(response.getLoginName());
            Cookie cookie = new Cookie("JWT", token);
            cookie.setPath("/");
            // 过期时间设为10min
            cookie.setMaxAge(60 * 10);
            httpServletResponse.addCookie(cookie);
            httpServletResponse.setHeader("access_token", token);
            UserContext.getInstance().setUser(response);
            request.getSession().setAttribute("uupUserSummary", response);
            return DataResult.ok("登录成功！", response);
        } else {
            return DataResult.fail("登录失败，请稍后重试！");
        }
    }

    @ResponseBody
    @NeedAuthority
    @RequestMapping(value = "/modifyUserInfo")
    public String modifyUserInfo() {
        ManagerLogin login1 = UserContext.getInstance().getUser();
        log.info("====" + login1.getLoginName());
        return "";
    }

    @RequestMapping(value = "/outLogin")
    public String outLogin(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie :
                cookies) {
            if (cookie.getName().equals("JWT")) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                break;

            }
        }
        UserContext.getInstance().removeUser();
        return "/manager/login";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manager/index";
    }
}
