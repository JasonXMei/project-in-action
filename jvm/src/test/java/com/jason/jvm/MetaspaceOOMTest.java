package com.jason.jvm;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class MetaspaceOOMTest {

    public interface Facade {
        void m(String input);
    }

    public static class FacadeImpl implements Facade {
        @Override
        public void m(String name) {
        }
    }

    public static class MetaspaceFacadeInvocationHandler implements InvocationHandler {
        private Object impl;

        public MetaspaceFacadeInvocationHandler(Object impl) {
            this.impl = impl;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(impl, args);
        }
    }

    private static Map<String, Facade> classLeakingMap = new HashMap<String, Facade>();

    private static void oom(HttpExchange exchange) {
        try {
            String response = "oom begin!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception ex) {
        }


        try {

            for (int i = 0; ; i++) {

                String jar = "file:" + i + ".jar";

                URL[] urls = new URL[]{new URL(jar)};
                URLClassLoader newClassLoader = new URLClassLoader(urls);

                Facade t = (Facade) Proxy.newProxyInstance(newClassLoader,
                        new Class<?>[]{Facade.class},
                        new MetaspaceFacadeInvocationHandler(new FacadeImpl()));

                classLeakingMap.put(jar, t);
            }
        } catch (Exception e) {
        }
    }

    private static void srv() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
        HttpContext context = server.createContext("/");
        context.setHandler(MetaspaceOOMTest::oom);
        server.start();
    }

    public static void main(String[] args) throws Exception {
        srv();
    }
}
