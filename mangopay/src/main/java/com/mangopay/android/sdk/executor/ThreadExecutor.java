package com.mangopay.android.sdk.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Executor implementation based on {@link ThreadPoolExecutor}. ThreadPoolExecutorConfig:
 * <p/>
 * Core pool size: 3.
 * Max pool size: 5.
 * Keep alive time: 120.
 * Time unit: seconds.
 * Work queue: {@link LinkedBlockingQueue}.
 * <p/>
 */
public class ThreadExecutor implements Executor {

  private static final int CORE_POOL_SIZE = 3;
  private static final int MAX_POOL_SIZE = 5;
  private static final int KEEP_ALIVE_TIME = 120;
  private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
  private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<Runnable>();

  private ThreadPoolExecutor mThreadPoolExecutor;

  public ThreadExecutor() {
    int corePoolSize = CORE_POOL_SIZE;
    int maxPoolSize = MAX_POOL_SIZE;
    int keepAliveTime = KEEP_ALIVE_TIME;
    TimeUnit timeUnit = TIME_UNIT;
    BlockingQueue<Runnable> workQueue = WORK_QUEUE;
    mThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, timeUnit, workQueue);
  }

  @Override public void run(final Interactor interactor) {
    if (null == interactor) {
      throw new IllegalArgumentException("Interactor to execute can't be null");
    }
    mThreadPoolExecutor.submit(new Runnable() {
      @Override public void run() {
        interactor.run();
      }
    });
  }
}
