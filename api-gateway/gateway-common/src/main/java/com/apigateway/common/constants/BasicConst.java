package com.apigateway.common.constants;

import java.util.regex.Pattern;

public interface BasicConst {

    String COLON_SEPARATOR = ":";

    String DIT_SEPARATOR = ".";

    String HTTP_PREFIX_SEPARATOR="http://";
    String HTTPS_PREFIX_SEPARATOR="https://";

    String HTTP_FORWARD_SEPARATOR = "X-Forwarded-For";

    Pattern PARAM_PATTERN = Pattern.compile("\\{(.*?)\\}");

    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

}
