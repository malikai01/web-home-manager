package com.mlk.web.home.manager.interceptor;

import com.mlk.web.home.manager.UserContext;
import com.mlk.web.home.manager.annotation.NeedAuthority;
import com.mlk.web.home.manager.filter.LoginAuthFilter;
import com.mlk.web.home.manager.model.po.ManagerLogin;
import com.mlk.web.home.manager.service.ManagerBaseService;
import com.mlk.web.home.manager.utils.CookieUtils;
import com.mlk.web.home.manager.utils.EmptyUtils;
import com.mlk.web.home.manager.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * NeedAuthority拦截器
 *
 * @author malikai
 * @date 2021-5-26 11:07
 */
@Component
public class NeedAuthorityInterceptor implements HandlerInterceptor {


    @Autowired
    private ManagerBaseService managerBaseService;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object _handler)
            throws Exception {
        if (!(_handler instanceof HandlerMethod)) {
            return true;
        }
        if (request.getRequestURI().contains("index")) {
            return true;
        }
        HandlerMethod handler = (HandlerMethod) _handler;
        //先看类上面是否有注解，如果有，则直接验证权限，如果没有则看方法上是否有注解。
        NeedAuthority authority = handler.getBean().getClass().getAnnotation(NeedAuthority.class);
        if (EmptyUtils.isEmpty(authority)) {
            authority = handler.getMethodAnnotation(NeedAuthority.class);
        }
        if (EmptyUtils.isEmpty(authority)) {
            return true;
        }
        if (!authority.isCheck()) {
            return true;
        }

        // 如果token为空，验证cookie
        String accessToken = LoginAuthFilter.getContext(LoginAuthFilter.Header_AccessToken1);
        ManagerLogin user = null;
        if (EmptyUtils.isEmpty(accessToken)) {
            // 从cookie中获取 jti=admin, iat=1531365819, sub=malikai@hujiang.com, iss=www.mlkfamilymanager.com, exp=3062731635
            Map<String, Object> map = CookieUtils.getTokenResolved(request);
            if (map.containsKey("jti")) {
                String name = (String) map.get("jti");
                user = managerBaseService.queryByLoginName(name);
            }/* else {

                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + "/manager/login");
            }*/
        } else {
            // validate token
            Map<String, Object> map = CookieUtils.getTokenResolved(request);
            String name = (String) map.get("jti");
            boolean flag = TokenUtils.validateJWT(map);
            if (flag) {
                user = managerBaseService.queryByLoginName(name);
            }
        }
        if (user == null) {
            throw new Exception("No Authority!");
        }
        UserContext.getInstance().setUser(user);
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
