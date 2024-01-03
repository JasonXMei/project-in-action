package com.jason.util;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * @Author Jason
 * @Date 2022/06/23
 */
public class HttpRetryStrategy implements HttpRequestRetryHandler {

    private final int maxRetries;
    private final long retryInterval;

    public HttpRetryStrategy(int maxRetries, long retryInterval) {
        Args.positive(maxRetries, "Max retries");
        Args.positive(retryInterval, "Retry interval");
        this.maxRetries = maxRetries;
        this.retryInterval = retryInterval;
    }

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        Args.notNull(exception, "Exception parameter");
        Args.notNull(context, "HTTP context");
        if (executionCount > this.maxRetries) {
            return false;
        } else {
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            if (exception instanceof SocketTimeoutException) {
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }

            return false;
        }
    }

}
