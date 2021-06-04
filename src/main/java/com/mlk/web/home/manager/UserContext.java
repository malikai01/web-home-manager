package com.mlk.web.home.manager;

/**
 *
 * @author malikai
 * @date 2021-6-4 14:14
 */

import com.mlk.web.home.manager.utils.EmptyUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class UserContext {

    private static volatile UserContext instance = null;

    private static final String S_KEY_USER = "S_KEY_USER";

    public static UserContext getInstance() {
        if (instance == null) {
            synchronized (UserContext.class) {
                if (instance == null) {
                    instance = new UserContext();
                }
            }
        }
        return instance;
    }

    /**
     * 获取用户
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (EmptyUtils.isEmpty(requestAttributes)) {
            return null;
        }
        return (T) requestAttributes.getAttribute(S_KEY_USER, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 保存用户
     *
     * @param t
     */
    public <T> void setUser(T t) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (EmptyUtils.isNotEmpty(requestAttributes)) {
            requestAttributes.setAttribute(S_KEY_USER, t, RequestAttributes.SCOPE_REQUEST);
        }
    }

    /**
     * 删除用户信息
     *
     * @see
     */
    public void removeUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (EmptyUtils.isNotEmpty(requestAttributes)) {
            requestAttributes.removeAttribute(S_KEY_USER, RequestAttributes.SCOPE_REQUEST);
        }
    }

}