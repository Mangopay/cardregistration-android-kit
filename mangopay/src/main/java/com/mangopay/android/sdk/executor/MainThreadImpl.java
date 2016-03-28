package com.mangopay.android.sdk.executor;

import android.content.Context;
import android.os.Handler;

public class MainThreadImpl implements MainThread {

  private Handler mHandler;

  public MainThreadImpl(Context context) {
    this.mHandler = new Handler(context.getMainLooper());
  }

  @Override public void post(Runnable runnable) {
    mHandler.post(runnable);
  }
}
