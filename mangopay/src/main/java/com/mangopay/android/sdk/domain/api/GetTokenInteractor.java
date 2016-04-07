package com.mangopay.android.sdk.domain.api;

import com.mangopay.android.sdk.model.exception.MangoException;

public interface GetTokenInteractor {
  interface Callback {
    void onGetTokenSuccess(String response);

    void onGetTokenError(MangoException error);
  }

  void execute(Callback callback, String cardRegistrationURL, String preregistrationData,
               String accessKeyRef, String cardNumber, String cardExpirationDate, String cardCvx);

}
