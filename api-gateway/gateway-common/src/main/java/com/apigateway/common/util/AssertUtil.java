package com.apigateway.common.util;


import io.netty.util.internal.StringUtil;

import java.util.Collection;
import java.util.Objects;

public class AssertUtil {

    public static void notEmpty(String str ,String message){
        if(StringUtil.isNullOrEmpty(str)){
            throw new RuntimeException(message);
        }
    }


    public static void assertNotEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new RuntimeException(message);
        }
    }

    public static void notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new RuntimeException(message);
        }
    }

    public static void isTrue(boolean value, String message) {
        if (!value) {
            throw new RuntimeException(message);
        }
    }

    public static void assertState(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

}
