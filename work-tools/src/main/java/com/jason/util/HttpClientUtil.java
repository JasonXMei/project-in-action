package com.jason.util;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.Map.Entry;

/**
 * HttpClient默认是有重试机制的，其重试策略是：
 * 1.只有发生IOExecetion时才会发生重试
 * 2.InterruptedIOException、UnknownHostException、ConnectException、SSLException，发生这4中异常不重试
 * 3.get方法可以重试3次，post方法在socket对应的输出流没有被write并flush成功时可以重试3次。
 * 4.读/写超时(SocketTimeoutException属于InterruptedIOException)不进行重试
 * 5.socket传输中被重置或关闭会进行重试
 * <p>
 * 构建httpClient实例的时候可手动禁止重试：
 * HttpClientBuilder.create().disableAutomaticRetries()
 * @Author Jason
 * @Date 2022/06/21
 */
@Slf4j
public class HttpClientUtil {

    private static final String ENCODING = "UTF-8";
    /** 设置连接超时时间，单位毫秒。 */
    private static final int CONNECT_TIMEOUT = 10000;
    /** 请求获取数据的超时时间(即响应时间)，单位毫秒。 */
    private static final int SOCKET_TIMEOUT = 10000;
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            .setConnectTimeout(CONNECT_TIMEOUT)
            .setSocketTimeout(SOCKET_TIMEOUT)
            .build();

    public static <P, T> T get(String url, Map<String, String> headers, P p, TypeReference<T> respClazz) {
        Map<String, String> params = ObjectUtil.obj2Map(p);
        printRequestMsg(url, headers, params);

        HttpGet httpGet = new HttpGet(packageParam(url, params));
        httpGet.setConfig(REQUEST_CONFIG);

        packageHeader(headers, httpGet);

        return getResponse(httpGet, respClazz);
    }

    public static <P, T> T postForm(String url, Map<String, String> headers, P p, TypeReference<T> respClazz) {
        Map<String, String> params = ObjectUtil.obj2Map(p);
        printRequestMsg(url, headers, params);

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(REQUEST_CONFIG);

        packageHeader(headers, httpPost);
        packageParam(params, httpPost);

        return getResponse(httpPost, respClazz);
    }

    public static <T> T postJson(String url, Map<String, String> headers, String params, TypeReference<T> respClazz) {
        printRequestMsg(url, headers, params);

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(REQUEST_CONFIG);

        packageHeader(headers, httpPost);
        packageParam(params, httpPost);

        return getResponse(httpPost, respClazz);
    }

    private static void printRequestMsg(String url, Map<String, String> headers, Map<String, String> params) {
        printRequestUrl(url);
        printRequestHeader(headers);

        if (Objects.nonNull(params)) {
            StringJoiner joiner = new StringJoiner(" | ");
            for (Entry<String, String> entry : params.entrySet()) {
                joiner.add(entry.getKey() + ":" + entry.getValue());
            }

            log.info("Params: [{}]", joiner.toString());
        }
    }

    private static void printRequestMsg(String url, Map<String, String> headers, String param) {
        printRequestUrl(url);
        printRequestHeader(headers);

        if (Objects.nonNull(param)) {
            log.info("Params: [{}]", param);
        }
    }

    private static void printRequestUrl(String url) {
        log.info("Request Url: [{}]", url);
    }

    private static void printRequestHeader(Map<String, String> headers) {
        if (Objects.nonNull(headers)) {
            StringJoiner joiner = new StringJoiner(" | ");
            for (Entry<String, String> entry : headers.entrySet()) {
                joiner.add(entry.getKey() + ":" + entry.getValue());
            }

            log.info("Headers: [{}]", joiner.toString());
        }
    }

    private static void packageHeader(Map<String, String> headers, HttpRequestBase httpMethod) {
        if (Objects.nonNull(headers)) {
            Set<Entry<String, String>> entrySet = headers.entrySet();
            for (Entry<String, String> entry : entrySet) {
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private static URI packageParam(String url, Map<String, String> params) {
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);

            if (Objects.nonNull(params)) {
                Set<Entry<String, String>> entrySet = params.entrySet();
                for (Entry<String, String> entry : entrySet) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue());
                }
            }

            return uriBuilder.build();
        } catch (URISyntaxException e) {
            log.error("package httpGet url failed", e);
            return null;
        }
    }

    private static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod) {
        if (Objects.nonNull(params)) {
            List<NameValuePair> nvps = new ArrayList<>();
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            try {
                httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
            } catch (UnsupportedEncodingException e) {
                log.error("package httpPost param failed", e);
            }
        }
    }

    private static void packageParam(String params, HttpEntityEnclosingRequestBase httpMethod) {
        if (Objects.nonNull(params)) {
            StringEntity entity = new StringEntity(params, ENCODING);
            entity.setContentType("application/json");
            httpMethod.setEntity(entity);
        }
    }

    private static <T> T getResponse(HttpRequestBase httpMethod, TypeReference<T> respClazz) {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpMethod);
            if (Objects.nonNull(httpResponse) && Objects.nonNull(httpResponse.getStatusLine())) {
                String content = "";
                if (Objects.nonNull(httpResponse.getEntity())) {
                    content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
                    log.info("Response: [{}]", content);
                }

                if (StrUtil.isNotEmpty(content)) {
                    return JSONUtil.toBean(content, respClazz, true);
                }
            }

            log.error("[{}] Response Empty", httpMethod.getURI());
        } catch (IOException e) {
            log.error("get response failed", e);
        } finally {
            release(httpResponse, httpClient);
        }
        return null;
    }

    private static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setRetryHandler(new HttpRetryStrategy(3, 5000L))
                .build();
        return httpClient;
    }

    private static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) {
        try {
            if (Objects.nonNull(httpResponse)) {
                httpResponse.close();
            }
            if (Objects.nonNull(httpClient)) {
                httpClient.close();
            }
        } catch (IOException e) {
            log.error("release http client failed", e);
        }
    }

}

