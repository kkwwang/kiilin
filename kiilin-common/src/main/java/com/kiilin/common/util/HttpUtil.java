package com.kiilin.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kiilin.common.abstracts.result.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * http 请求帮助类
 *
 * @author wangKai
 * @version $Id: HttpUtil.java, v 0.1 2017年5月8日 上午10:01:16 wangKai Exp $
 */
@Slf4j
public class HttpUtil {

    public static final String IS_BYTE_DATA = "IS_BYTE_DATA";
    public static final String DEFAULT_CHARSET = "utf-8";


    /**
     * 超时时间
     */
    private static final Integer TIME_OUT = 500000;

    /**
     * httpClient的get请求方式
     *
     * @param url     url
     * @param charset 字符集
     * @return
     */
    public static ServiceResult get(String url, String charset) {

        Date start = new Date();
        // 请求结果
        ServiceResult result = ServiceResult.success();
        LogUtils.info(log, "调用HttpClinet服务URL, url = {0}", url);

        // 生成 HttpClinet 对象并设置参数
        HttpClient httpClient = new HttpClient();

        // 设置 Http 连接超时为5秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT);

        // 生成 GetMethod 对象并设置参数
        GetMethod getMethod = new GetMethod(url);

        // 设置 get 请求超时为 5 秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIME_OUT);

        // 设置请求重试处理，用的是默认的重试处理：请求三次
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        // 执行 HTTP GET 请求
        try {

            int statusCode = httpClient.executeMethod(getMethod);
            // 判断访问的状态码
            if (statusCode == HttpStatus.SC_OK) {
                result.setCode(String.valueOf(statusCode));

                // 读取 HTTP 响应内容，
                // 读取为字节数组
                byte[] responseBody = getMethod.getResponseBody();

                // byte数组时直接返回
                if (StringUtils.equalsIgnoreCase(IS_BYTE_DATA, charset)) {
                    Date end = new Date();
                    LogUtils.info(log, "调用HttpClinet服务完成,请求结果 result = byte[{0}] 执行时间 {1} ms", responseBody.length, end.getTime() - start.getTime());
                    return result.setData(responseBody);
                }
                String response = new String(responseBody, Charset.forName(charset));
                LogUtils.info(log, "调用HttpClient返回结果responseBody = {0}", response);

                result.setData(JSON.parse(response));
                Date end = new Date();
                LogUtils.info(log, "调用HttpClinet服务完成,请求结果 result = {0} 执行时间 {1} ms", result.getError(), end.getTime() - start.getTime());
            } else {
                LogUtils.error(log, "请求出错: " + getMethod.getStatusLine(), null);
            }
        } catch (IOException e) {
            LogUtils.error(log, "调用HttpClient时发生异常", e);
        } finally {
            getMethod.releaseConnection();
        }
        return result;
    }

    public static ServiceResult get(String url) {
        return get(url, DEFAULT_CHARSET);
    }


    /**
     * post请求
     *
     * @param url     url
     * @param json    json 参数
     * @param pairs   NameValuePair[]
     * @param param   string 参数 ?& 拼接
     * @param charset 字符集
     * @return
     */
    public static ServiceResult post(String url, String json, NameValuePair[] pairs, String param, String charset) {

        charset = StringUtils.isBlank(charset) ? DEFAULT_CHARSET : charset;
        Date start = new Date();
        // 请求结果
        ServiceResult result = ServiceResult.success();
        LogUtils.info(log, "调用HttpClinet服务URL, url = {0}", url);

        // 生成 HttpClinet 对象并设置参数
        HttpClient httpClient = new HttpClient();

        // 设置 Http 连接超时为5秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT);

        // 生成 PostMethod 对象并设置参数
        PostMethod postMethod = new PostMethod(url);

        // 设置 get 请求超时为 5 秒
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIME_OUT);

        // 设置默认请求头
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());


        // 处理请求参数
        try {
            if (pairs != null && pairs.length > 0) {
                postMethod.setQueryString(pairs);
            } else {
                postMethod.setQueryString(param);
            }
            // 非空保护
            if (StringUtils.isNotBlank(json)) {
                RequestEntity requestEntity = new StringRequestEntity(json, "application/json", charset);
                postMethod.setRequestEntity(requestEntity);
            }

            LogUtils.info(log, "调用HttpClient参数 json = {0}", json);

            int statusCode = httpClient.executeMethod(postMethod);
            // 判断访问的状态码
            if (statusCode == HttpStatus.SC_OK) {

                result.setCode(String.valueOf(statusCode));
                result.setSuccess(true);


                // 读取 HTTP 响应内容，
                // 读取为字节数组
                byte[] responseBody = postMethod.getResponseBody();
                String response = new String(responseBody, Charset.forName(charset));
                LogUtils.info(log, "调用HttpClient返回结果responseBody = {0}", response);

                result.setData(JSONObject.parse(response));
                Date end = new Date();
                LogUtils.info(log, "调用HttpClinet服务完成,请求结果 result = {0} 执行时间 {1} ms", result.getError(), end.getTime() - start.getTime());

            } else {
                LogUtils.error(log, "请求出错: " + postMethod.getStatusLine(), null);
            }

        } catch (UnsupportedEncodingException e) {
            LogUtils.error(log, "调用远程服务失败", e);
        } catch (HttpException e) {
            LogUtils.error(log, "调用HttpClient时发生异常", e);
        } catch (IOException e) {
            LogUtils.error(log, "调用HttpClient时发生异常", e);
        }

        return result;
    }


    public static ServiceResult post(String url) {
        return HttpUtil.post(url, DEFAULT_CHARSET);
    }

    public static ServiceResult post(String url, JSONObject json) {
        return post(url, json, null);
    }

    public static ServiceResult post(String url, String param) {
        return post(url, null, null, param, DEFAULT_CHARSET);
    }

    public static ServiceResult post(String url, NameValuePair[] pairs) {
        return post(url, null, pairs, null, null);
    }

    public static ServiceResult post(String url, JSONObject json, String charset) {
        return post(url, json.toJSONString(), null, null, charset);
    }


    /**
     * 获取请求ip
     *
     * @param request
     * @return
     */
    private static final int IP_MAX_LENGTH = 15;
    private static final String IP_SEPARATOR = ",";
    public static String getRequestIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        String unknown = "unknown";
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }


        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割

        if (ip != null && ip.length() > IP_MAX_LENGTH) {
            if (ip.indexOf(IP_SEPARATOR) > 0) {
                ip = ip.substring(0, ip.indexOf(IP_SEPARATOR));
            }
        }

        return ip;

    }

}
