package com.vova.rule.common.utils;

import java.util.Arrays;
import java.util.Objects;

/**
 * parameter util
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */
public class ParameterUtil {

    private ParameterUtil() {}

    public static boolean isAnyNull(Object... objects) {
        if (null == objects) {
            return true;
        }
        return Arrays.stream(objects).anyMatch(Objects::isNull);
    }
}
