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
import com.mangopay.android.sdk.model.MangoCard;
import com.mangopay.android.sdk.model.MangoSettings;
import com.mangopay.android.sdk.model.exception.MangoError;
import com.mangopay.android.sdk.util.PrintLog;

public class MangoPay {

  private Callback mCallback;

  private MangoSettings mSettings;
  private MangoCard mCard;
  private MainThread mMainThread;

  protected MangoPay(Context context, String baseURL, String clientId, String cardPreregistrationId,
                     String cardRegistrationURL, String preregistrationData,
                     String accessKey, String cvx, String cardNumber, String expirationDate) {
    this(context, new MangoSettings(baseURL, clientId, cardPreregistrationId, cardRegistrationURL,
            preregistrationData, accessKey), new MangoCard(cardNumber, expirationDate, cvx));
  }

  protected MangoPay(Context context, MangoSettings mSettings, MangoCard mCard) {
    this.mMainThread = new MainThreadImpl(context.getApplicationContext());
    this.mSettings = mSettings;
    this.mCard = mCard;
  }

  public MangoPay(Context context, MangoSettings mSettings) {
    this.mMainThread = new MainThreadImpl(context.getApplicationContext());
    this.mSettings = mSettings;
  }

  /**
   * Starts the card registration process
   */
  public void registerCard(MangoCard card, Callback callback) {
    PrintLog.debug("MangoPay SDK register card started");
    this.mCallback = callback;

    if (mCard == null)
      mCard = card;

    GetTokenInteractor.Callback serviceCallback = new GetTokenInteractor.Callback() {
      @Override public void onGetTokenSuccess(String response) {
        getTokenSuccess(response);
      }

      @Override public void onGetTokenError(MangoError error) {
        getTokenError(error);
      }
    };
    GetTokenInteractor getTokenInteractor = new GetTokenInteractorImpl(new ThreadExecutor(), mMainThread);

    try {
      mSettings.validate();
      mCard.validate();

      getTokenInteractor.execute(serviceCallback, mSettings.getCardRegistrationURL(),
              mSettings.getPreregistrationData(), mSettings.getAccessKey(),
              mCard.getCardNumber(), mCard.getExpirationDate(), mCard.getCvx());
    } catch (MangoError error) {
      if (mCallback != null)
        mCallback.failure(error);
    } catch (NullPointerException e) {
      if (mCallback != null)
        mCallback.failure(new MangoError(e));
    }
  }

  private void getTokenSuccess(String response) {
    CardRegistrationInteractor.Callback serviceCallback = new CardRegistrationInteractor.Callback() {
      @Override public void onCardRegistrationSuccess(CardRegistration response) {
        cardRegistrationSuccess(response);
      }

      @Override public void onCardRegistrationError(MangoError error) {
        cardRegistrationError(error);
      }
    };

    CardRegistrationInteractor cardRegistrationInteractor =
            new CardRegistrationInteractorImpl(new ThreadExecutor(), mMainThread);

    try {
      mSettings.validate();

      cardRegistrationInteractor.execute(serviceCallback, mSettings.getBaseURL(),
              mSettings.getClientId(), mSettings.getCardPreregistrationId(), response);
    } catch (MangoError error) {
      if (mCallback != null)
        mCallback.failure(error);
    }
  }

  private void getTokenError(MangoError error) {
    PrintLog.error(error.toString());
    if (mCallback != null)
      mCallback.failure(error);
  }

  private void cardRegistrationSuccess(CardRegistration response) {
    PrintLog.debug("SUCCESS: " + response.toString());
    if (mCallback != null)
      mCallback.success(response);
  }

  private void cardRegistrationError(MangoError error) {
    PrintLog.error(error.toString());
    if (mCallback != null)
      mCallback.failure(error);
  }
}
