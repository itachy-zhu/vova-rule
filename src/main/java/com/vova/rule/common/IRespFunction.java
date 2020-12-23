package com.vova.rule.common;

/**
 * handle response result
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-06-13
 */

@FunctionalInterface
public interface IRespFunction<T, R> {

    /**
     * 消费响应数据，返回结果
     *
     * @param t 响应数据
     * @return 返回对应结果
     */
    R handle(T t);
}