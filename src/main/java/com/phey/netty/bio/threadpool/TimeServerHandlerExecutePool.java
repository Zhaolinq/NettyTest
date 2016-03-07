package com.phey.netty.bio.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerHandlerExecutePool {
    private static volatile ExecutorService executor;
    private static final int maxPoolSize = Runtime.getRuntime().availableProcessors();
    private static final int queueSize = 1;
    
    private TimeServerHandlerExecutePool() {
    }
    
    public static void execute(Runnable task) {
        if(executor == null) {
            synchronized(TimeServerHandlerExecutePool.class) {
                if(executor == null) {
                    executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, 
                            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
                }
            }
        }
        executor.execute(task);
    }
}
