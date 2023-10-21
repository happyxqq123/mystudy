package com.apigateway.core.request;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.cookie.Cookie;
import org.asynchttpclient.Request;

/**
 * 提供可供修改的Request参数操作接口
 */
public interface IGatewayRequest {

    /**
     * 修改目标服务地址
     * @param host
     */
    void setModifyHost(String host);

    /**
     * 获取目标服务地址
     * @return
     */
    String getModifyHost();

    /**
     * 设置目标服务路径
     */
    void setModifyPath(String path);

    /**
     * 获取目标服务路径
     * @return
     */
    String getModifyPath();

    /**
     * 添加请求头
     * @param name
     * @param value
     */
    void addHeader(CharSequence name, String value);

    /**
     * 设置请求头信息
     * @param name
     * @param value
     */
    void setHeader(CharSequence name, String value);

    /**
     * 添加请求参数
     * @param name
     * @param value
     */
    void addQueryParam(String name, String value);

    /**
     * 添加表单请求参数
     * @param name
     * @param value
     */
    void addFormParam(String name,String value);

    /**
     * 添加或者替换Cookie
     * @param cookie
     */
    void addOrReplaceCookie(Cookie cookie);

    /**
     * 设置超时时间
     * @param requestTimeout
     */
    void setRequestTimeout(int requestTimeout);

    /**
     * 获取最终的请求路径，包含请求参数 Http://localhost:8081/api/admin?name=a1
     */
    String getFinalUrl();

    Request build();

}
