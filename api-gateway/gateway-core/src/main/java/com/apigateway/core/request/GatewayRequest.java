package com.apigateway.core.request;

import com.apigateway.common.constants.BasicConst;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.util.internal.StringUtil;
import lombok.Getter;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;

import java.nio.charset.Charset;
import java.util.*;

@Getter
public class GatewayRequest implements IGatewayRequest {

    /**
     * 服务唯一ID
     */
    @Getter
    private final String uniqueId;

    /***
     * 进入网关的开始时间
     */
    @Getter
    private final long beginTime;

    /**
     * 进入网关的结束时间
     */
    @Getter
    private final long endTime;

    /**
     * 字符集
     */
    @Getter
    private final Charset charset;

    /**
     * 客户端的ip
     */
    @Getter
    private final String clientIP;

    /**
     * 服务端的主机名
     */
    @Getter
    private final String host;

    /**
     * 服务端请求路径 /xx/xx/xxx
     */
    @Getter
    private final String path;

    /**
     * 统一资源标识符 /xx/xx/xxx?a1=1&a2=2
     */
    private final String uri;

    /**
     * 请求方式 Post
     */
    private final HttpMethod method;

    /**
     * 请求的格式
     *
     * @param host
     */
    private final String contentType;

    /**
     * 请求头
     */
    private final HttpHeaders headers;

    /**
     * 参数解析器
     */
    private final QueryStringDecoder queryStringDecoder;

    /**
     * @param host
     */
    private final FullHttpRequest fullHttpRequest;

    /**
     * 请求体
     */
    private String body;

    /**
     * 请求体
     */
    private Map<String, Cookie> cookieMap;


    /**
     * Post请求参数
     */
    private Map<String, List<String>> postParameters;

    /**
     * 可修改的Scheme,默认为Http://
     */
    private String modifyScheme;

    /**
     * 可修改的主机名
     */
    private String modifyHost;

    /**
     * 可修改的路径
     */
    private String modifyPath;


    /**
     * 构建下游请求时的http构建器
     */
    private final RequestBuilder requestBuilder;

    public GatewayRequest(String uniqueId, long beginTime, long endTime, Charset charset,
                          String clientIP, String host, String path, String uri,
                          HttpMethod httpMethod, String contentType, HttpHeaders httpHeaders,
                          QueryStringDecoder queryStringDecoder, FullHttpRequest fullHttpRequest,
                          RequestBuilder requestBuilder, HttpMethod method, HttpHeaders headers) {
        this.uniqueId = uniqueId;
        this.method = method;
        this.headers = headers;
        this.beginTime = System.currentTimeMillis();
        this.endTime = endTime;
        this.charset = charset;
        this.clientIP = clientIP;
        this.host = host;
        this.uri = uri;
        this.contentType = contentType;
        this.queryStringDecoder = new QueryStringDecoder(uri,charset);
        this.fullHttpRequest = fullHttpRequest;
        this.requestBuilder = new RequestBuilder();
        this.path = this.queryStringDecoder.path();

        this.modifyHost= host;
        this.modifyPath = path;
        this.modifyScheme = BasicConst.HTTP_PREFIX_SEPARATOR;
        this.requestBuilder.setMethod(getMethod().name());
        this.requestBuilder.setHeaders(getHeaders());
        this.requestBuilder.setQueryParams(queryStringDecoder.parameters());
        ByteBuf contentBuffer = fullHttpRequest.content();
        if(Objects.nonNull(contentBuffer)){
            this.requestBuilder.setBody(contentBuffer.nioBuffer());
        }
    }

    /**
     * 获取请求体
     * @return
     */
    public String getBody(){
        if(StringUtil.isNullOrEmpty(body)){
            body = fullHttpRequest.content().toString(charset);
        }
        return body;
    }

    /**
     * 获取指定的cookie
     * @param name
     * @return
     */
    public Cookie getCookie(String name){
        if(StringUtil.isNullOrEmpty(name)) return null;
        if(cookieMap == null){
            cookieMap = new HashMap<>();
            String cookieStr = getHeaders().get(HttpHeaderNames.COOKIE);
            Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieStr);
            cookies.forEach(cookie -> {
                cookieMap.put(cookie.name(),cookie);
            });
        }
        return cookieMap.get(name);
    }

    /**
     * 获取指定名称的参数值
     * @param name
     * @return
     */
    public List<String> getQueryParametersMultiple(String name){
        return queryStringDecoder.parameters().get(name);
    }

    public List<String> getPostParametersMultiple(String name){
        String body = getBody();
        if(isFormPost()){
            if(Objects.isNull(postParameters)){
                QueryStringDecoder paramDecoder = new QueryStringDecoder(body,false);
                postParameters =  paramDecoder.parameters();
            }
            if(postParameters == null || postParameters.isEmpty()){
                return null;
            }else{
                return postParameters.get(name);
            }
        }else if(isJsonPost()){
            return Lists.newArrayList(JsonPath.read(body,name).toString());
        }
        return null;
    }

    private boolean isJsonPost() {
        return  HttpMethod.POST.equals(method)&
                (contentType.startsWith(HttpHeaderValues.APPLICATION_JSON.toString()));
    }

    private boolean isFormPost() {
        return HttpMethod.POST.equals(method) &&
                (contentType.startsWith(HttpHeaderValues.FORM_DATA.toString()) ||
                        contentType.startsWith(HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString()));
    }


    @Override
    public void setModifyHost(String host) {
        this.modifyHost = host;
    }

    @Override
    public String getModifyHost() {
        return modifyHost;
    }

    @Override
    public void setModifyPath(String path) {
        this.modifyPath = path;
    }

    @Override
    public String getModifyPath() {
        return path;
    }

    @Override
    public void addHeader(CharSequence name, String value) {
        requestBuilder.addHeader(name,value);
    }

    @Override
    public void setHeader(CharSequence name, String value) {
        requestBuilder.setHeader(name,value);
    }

    @Override
    public void addQueryParam(String name, String value) {
        requestBuilder.addQueryParam(name,value);
    }

    @Override
    public void addFormParam(String name, String value) {
        if(isFormPost()){
            requestBuilder.addFormParam(name,value);
        }
    }

    @Override
    public void addOrReplaceCookie(Cookie cookie) {
        requestBuilder.addOrReplaceCookie(cookie);
    }

    @Override
    public void setRequestTimeout(int requestTimeout) {
        requestBuilder.setRequestTimeout(requestTimeout);
    }

    @Override
    public String getFinalUrl() {
        return modifyScheme +modifyHost +modifyPath;
    }

    @Override
    public Request build() {
        requestBuilder.setUrl(getFinalUrl());
        return requestBuilder.build();
    }
}
