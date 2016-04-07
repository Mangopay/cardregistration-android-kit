package com.mangopay.android.sdk.domain;

import com.mangopay.android.sdk.domain.api.CardRegistrationInteractor;
import com.mangopay.android.sdk.domain.service.CardService;
import com.mangopay.android.sdk.domain.service.CardServiceImpl;
import com.mangopay.android.sdk.domain.service.ServiceCallback;
import com.mangopay.android.sdk.executor.Executor;
import com.mangopay.android.sdk.executor.Interactor;
import com.mangopay.android.sdk.executor.MainThread;
import com.mangopay.android.sdk.model.CardRegistration;
import com.mangopay.android.sdk.model.CardRegistrationRequest;
import com.mangopay.android.sdk.model.exception.MangoException;
import com.mangopay.android.sdk.util.JsonUtil;

/**
 * Card registration request implementation
 */
public class CardRegistrationInteractorImpl extends BaseInteractorImpl<CardService>
        implements Interactor, CardRegistrationInteractor {

  private String mBaseUrl;
  private String mClientId;
  private String mId;
  private String mRegData;

  private Callback mCallback;

  public CardRegistrationInteractorImpl(Executor executor, MainThread mainThread) {
    super(executor, mainThread, new CardServiceImpl());
  }

  @Override public void execute(Callback callback, String baseURL, String clientId,
                                String id, String registrationData) {
    validateCallbackSpecified(callback);
    this.mCallback = callback;
    this.mBaseUrl = baseURL;
    this.mClientId = clientId;
    this.mId = id;
    this.mRegData = registrationData;
    this.mExecutor.run(this);
  }

  @Override public void run() {
    String url = mBaseUrl + "/v2/" + mClientId + "/CardRegistrations/" + mId;
    CardRegistrationRequest requestBody = new CardRegistrationRequest(mId, mRegData);

    ServiceCallback<String> callback = new ServiceCallback<String>() {
      @Override public void success(String response) {
        CardRegistration cardRegistration = JsonUtil.getCardRegistration(this, response);
        if (cardRegistration != null)
          notifySuccess(cardRegistration);
      }

      @Override public void failure(MangoException error) {
        notifyError(error);
      }
    };

    mService.post(url, requestBody.getFields(callback), "application/json", callback);
  }

  private void notifySuccess(final CardRegistration response) {
    mMainThread.post(new Runnable() {
      @Override public void run() {
        mCallback.onCardRegistrationSuccess(response);
      }
    });
  }

  private void notifyError(final MangoException message) {
    mMainThread.post(new Runnable() {
      @Override public void run() {
        mCallback.onCardRegistrationError(message);
      }
    });
  }

}
