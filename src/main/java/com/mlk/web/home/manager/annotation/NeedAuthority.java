package com.mlk.web.home.manager.annotation;

import java.lang.annotation.*;

/**
 * Created by malikai on 2018-7-12.
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedAuthority {

    boolean isCheck() default true;

}
