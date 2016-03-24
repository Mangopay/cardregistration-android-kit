package com.mangopay.android.sdk.domain.api;

public interface GetTokenInteractor {
  interface Callback {
    void onGetTokenSuccess(String response);

    void onGetTokenError(String message);
  }

  void execute(Callback callback, String cardRegistrationURL, String preregistrationData,
               String accessKeyRef, String cardNumber, String cardExpirationDate, String cardCvx);

}
