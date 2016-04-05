package com.mangopay.android.sdk.domain;

import com.mangopay.android.sdk.executor.Executor;
import com.mangopay.android.sdk.executor.MainThread;

/**
 * Holds common threading initialization used inside any interactor
 *
 * @param <Service>
 */
public abstract class BaseInteractorImpl<Service> {

  protected final Executor mExecutor;
  protected final MainThread mMainThread;
  protected final Service mService;

  public BaseInteractorImpl(Executor mExecutor, MainThread mMainThread, Service mService) {
    this.mMainThread = mMainThread;
    this.mService = mService;
    this.mExecutor = mExecutor;
  }

  protected final void validateCallbackSpecified(Object callback) throws IllegalArgumentException {
    if (callback == null) {
      throw new IllegalArgumentException("Callback can't be null, the client of this interactor " +
              "needs to get the response in the callback");
    }
  }
}
