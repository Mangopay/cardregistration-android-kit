package com.mangopay.android.sdk;

import android.content.Context;

import com.mangopay.android.sdk.domain.CardRegistrationInteractorImpl;
import com.mangopay.android.sdk.domain.GetTokenInteractorImpl;
import com.mangopay.android.sdk.domain.api.CardRegistrationInteractor;
import com.mangopay.android.sdk.domain.api.GetTokenInteractor;
import com.mangopay.android.sdk.executor.MainThread;
import com.mangopay.android.sdk.executor.MainThreadImpl;
import com.mangopay.android.sdk.executor.ThreadExecutor;
import com.mangopay.android.sdk.model.CardRegistration;
import com.mangopay.android.sdk.model.MangoError;

public class MangoPaySDK implements GetTokenInteractor.Callback, CardRegistrationInteractor.Callback {
  private Callback mCallback;

  private String mBaseURL;
  private String mClientId;
  private String mCardPreregistrationId;
  private MainThread mMainThread;

  protected MangoPaySDK(Callback callback, String baseURL, String clientId,
                        String cardPreregistrationId, String cardRegistrationURL,
                        String preregistrationData, String accessKey,
                        String cardNumber, String expirationDate,
                        String cvx, Context context) {
    this.mCallback = callback;
    this.mBaseURL = baseURL;
    this.mClientId = clientId;
    this.mCardPreregistrationId = cardPreregistrationId;
    this.mMainThread = new MainThreadImpl(context);

    GetTokenInteractor getTokenInteractor = new GetTokenInteractorImpl(new ThreadExecutor(), mMainThread);
    getTokenInteractor.execute(this, cardRegistrationURL, preregistrationData,
            accessKey, cardNumber, expirationDate, cvx);
  }

  @Override public void onGetTokenSuccess(String response) {
    CardRegistrationInteractor cardRegistrationInteractor = new CardRegistrationInteractorImpl(new ThreadExecutor(), mMainThread);
    cardRegistrationInteractor.execute(this, mBaseURL, mClientId, mCardPreregistrationId, response);
  }

  @Override public void onGetTokenError(MangoError error) {
    if (mCallback != null)
      mCallback.failure(error);
  }

  @Override public void onCardRegistrationSuccess(CardRegistration response) {
    if (mCallback != null)
      mCallback.success(response);
  }

  @Override public void onCardRegistrationError(MangoError error) {
    if (mCallback != null)
      mCallback.failure(error);
  }
}
