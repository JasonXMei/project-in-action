package com.jason.util;

public class FunctionHelper {

    public interface Invokable<T> {
        T invoke() throws Exception;

    }

    /**
     * 用于统一处理方法需要 try catch 的场景
     * @param invokable
     * @return
     * @param <T>
     */
    public static <T> T implicit(Invokable<T> invokable) {
        T result = null;
        try {
            result = invokable.invoke();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}
