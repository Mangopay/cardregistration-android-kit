package com.mangopay.android.sdk.executor;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the {@link Interactor} out of the UI thread.
 * <p/>
 * Use this class to execute an {@link Interactor}.
 * <p/>
 * This is just a sample implementation of how a Interactor/Executor environment can be
 * implemented.
 * Ideally interactors should not know about Executor or MainThread dependency. Interactors client
 * code should get a Executor instance to execute interactors.
 * <p/>
 */
public interface Executor {

  void run(final Interactor interactor);
}