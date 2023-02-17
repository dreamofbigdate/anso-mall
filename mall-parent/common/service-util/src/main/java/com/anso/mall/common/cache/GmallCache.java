package com.anso.mall.common.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-02 14:11
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GmallCache {
    /**
     * 缓存key的前缀
     *
     * @return
     */
    String prefix() default "cache";
}
