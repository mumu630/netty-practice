package com.crazy.common;

import java.util.concurrent.*;

public class BusinessThreadPool {
    private static ExecutorService pool = new ThreadPoolExecutor(10, 10, 
            1000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());

    public static Future submit(Callable callable){
        return pool.submit(callable);
    }
}
