package com.jason;

import cn.hutool.json.JSONUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author Jason
 * @Date 2022/07/19
 */
public class GuavaTest {

    // 最佳实践，在 get 的时候，是先判断过期，再判断refresh，即如果过期了会优先调用 load 方法（阻塞其他线程），在不过期情况下且过了refresh时间才去做 reload （异步加载，同时返回旧值），
    // 所以推荐的设置是 refresh < expire，这个设置还可以解决一个场景就是，如果长时间没有访问缓存，可以保证 expire 后可以取到最新的值，而不是因为 refresh 取到旧值
    private static LoadingCache<String, String > loadingCache = CacheBuilder.newBuilder()
            //最大容量为100（基于容量进行回收）
            .maximumSize(100)
            //配置写入后多久使缓存过期, 解决缓存击穿问题, 当大量线程用相同的key获取缓存值时，只会有一个线程进入load方法，而其他线程则等待，直到缓存值被生成, 在高并发，会阻塞大量线程
            .expireAfterWrite(30, TimeUnit.MINUTES)
            //配置写入后多久刷新缓存，由于 expireAfterWrite 会导致其他线程阻塞，故该配置使更新线程调用 reload 方法更新该缓存，其他请求线程返回该缓存的旧值，解决线程阻塞问题
            .refreshAfterWrite(20, TimeUnit.MINUTES)
            //创建一个CacheLoader，重写load方法，以实现"当get时缓存不存在，则load，放到缓存，并返回"的效果
            .build(new CacheLoader<String, String>() {
                //重点，自动写缓存数据的方法，必须要实现
                @Override
                public String load(String key) throws Exception {
                    System.out.println(key);
                    return "value_" + key;
                }

                //异步刷新缓存， 解决缓存雪崩问题
                @Override
                public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                    return super.reload(key, oldValue);
                }
            });


    @Test
    public void test() throws ExecutionException {
        loadingCache.get("1");
        loadingCache.get("1");
    }
}
