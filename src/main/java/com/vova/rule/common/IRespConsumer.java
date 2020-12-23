package com.vova.rule.common;

/**
 * handle response result
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-06-13
 */

@FunctionalInterface
public interface IRespConsumer<T> {

    /**
     * 消费响应数据
     *
     * @param t 响应数据
     */
    void consume(T t);
}