package com.mangopay.android.sdk.domain;

import com.mangopay.android.sdk.domain.api.GetTokenInteractor;
import com.mangopay.android.sdk.domain.service.CardService;
import com.mangopay.android.sdk.domain.service.CardServiceImpl;
import com.mangopay.android.sdk.domain.service.ServiceCallback;
import com.mangopay.android.sdk.executor.Executor;
import com.mangopay.android.sdk.executor.Interactor;
import com.mangopay.android.sdk.executor.MainThread;
import com.mangopay.android.sdk.model.CreateTokenRequest;
import com.mangopay.android.sdk.model.exception.MangoException;

/**
 * Get Token request implementation
 */
public class GetTokenInteractorImpl extends BaseInteractorImpl<CardService>
        implements Interactor, GetTokenInteractor {

  private String mRegistrationURL;
  private String mPreData;
  private String mAccessKey;
  private String mCardNumber;
  private String mExpirationDate;
  private String mCardCvx;

  private Callback mCallback;

  public GetTokenInteractorImpl(Executor executor, MainThread mainThread) {
    super(executor, mainThread, new CardServiceImpl());
  }

  @Override public void execute(Callback callback, String cardRegistrationURL,
                                String preregistrationData, String accessKeyRef,
                                String cardNumber, String cardExpirationDate, String cardCvx) {
    validateCallbackSpecified(callback);
    this.mCallback = callback;
    this.mRegistrationURL = cardRegistrationURL;
    this.mPreData = preregistrationData;
    this.mAccessKey = accessKeyRef;
    this.mCardNumber = cardNumber;
    this.mExpirationDate = cardExpirationDate;
    this.mCardCvx = cardCvx;
    this.mExecutor.run(this);
  }

  @Override public void run() {
    CreateTokenRequest requestBody = new CreateTokenRequest(mPreData, mAccessKey, mCardNumber,
            mExpirationDate, mCardCvx);

    ServiceCallback<String> callback = new ServiceCallback<String>() {
      @Override public void success(String response) {
        notifySuccess(response);
      }

      @Override public void failure(MangoException error) {
        notifyError(error);
      }
    };

    mService.post(mRegistrationURL, requestBody.getFields(callback), "text/plain", callback);
  }

  private void notifySuccess(final String response) {
    mMainThread.post(new Runnable() {
      @Override public void run() {
        mCallback.onGetTokenSuccess(response);
      }
    });
  }

  private void notifyError(final MangoException message) {
    mMainThread.post(new Runnable() {
      @Override public void run() {
        mCallback.onGetTokenError(message);
      }
    });
  }
}
