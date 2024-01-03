package com.jason.nettydemo.util;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketHandler {
    private static final Map<Integer, NioSocketChannel> CHANNEL_MAP_USE = new ConcurrentHashMap<>(3000);
    private static final Map<Integer, NioSocketChannel> CHANNEL_MAP_CLIENT = new ConcurrentHashMap<>(3000);

    public static void putUse(Integer id, NioSocketChannel socketChannel) {
        CHANNEL_MAP_USE.put(id, socketChannel);
    }

    public static NioSocketChannel getUse(Integer id) {
        return CHANNEL_MAP_USE.get(id);
    }

    public static void removeUse(NioSocketChannel nioSocketChannel) {
        CHANNEL_MAP_USE.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).forEach(entry -> CHANNEL_MAP_USE.remove(entry.getKey()));
    }

    public static void putClient(Integer id, NioSocketChannel socketChannel) {
        CHANNEL_MAP_CLIENT.put(id, socketChannel);
    }

    public static NioSocketChannel getClient(Integer id) {
        return CHANNEL_MAP_CLIENT.get(id);
    }

    public static void removeClient(NioSocketChannel nioSocketChannel) {
        CHANNEL_MAP_CLIENT.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).forEach(entry -> CHANNEL_MAP_CLIENT.remove(entry.getKey()));
    }
}
