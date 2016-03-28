package com.mangopay.android.sdk.domain.api;

import com.mangopay.android.sdk.model.MangoError;

public interface GetTokenInteractor {
  interface Callback {
    void onGetTokenSuccess(String response);

    void onGetTokenError(MangoError error);
  }

  void execute(Callback callback, String cardRegistrationURL, String preregistrationData,
               String accessKeyRef, String cardNumber, String cardExpirationDate, String cardCvx);

}
