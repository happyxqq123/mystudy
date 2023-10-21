package com.apigateway.common.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONTool {

    public static final String CODE = "code";

    public static final String STATUS = "status";

    public static final String DATA = "data";

    public static final String MESSAGE = "message";

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final JsonFactory jsonFactroy = mapper.getFactory();




}
