package com.mangopay.android.sdk.domain.service;

import com.mangopay.android.sdk.model.exception.MangoError;

public interface ServiceCallback<T> {

  void success(T jsonResponse);

  void failure(MangoError error);
}
