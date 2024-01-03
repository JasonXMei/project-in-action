package com.jason;

/**
 * 每个线程内需要保存类似于全局变量的信息（例如在拦截器中获取的用户信息）
 * @Author Jason
 * @Date 2023-05-12
 */
public class ThreadLocalDemo02 {

    public static void main(String[] args) {
        new Service1().service1();
    }

}

class Service1 {

    public void service1() {
        User user = new User("Jason");
        UserContextHolder.holder.set(user);
        new Service2().service2();
    }

}

class Service2 {

    public void service2() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service2拿到用户名：" + user.name);
        new Service3().service3();
    }

}

class Service3 {

    public void service3() {
        User user = UserContextHolder.holder.get();
        System.out.println("Service3拿到用户名：" + user.name);
        UserContextHolder.holder.remove();
    }

}

class UserContextHolder {

    public static ThreadLocal<User> holder = new ThreadLocal<>();

}

class User {

    String name;

    public User(String name) {
        this.name = name;
    }

}
