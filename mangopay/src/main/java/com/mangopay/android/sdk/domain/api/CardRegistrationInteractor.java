package com.mangopay.android.sdk.domain.api;

import com.mangopay.android.sdk.model.CardRegistration;
import com.mangopay.android.sdk.model.exception.MangoException;

public interface CardRegistrationInteractor {
  interface Callback {
    void onCardRegistrationSuccess(CardRegistration response);

    void onCardRegistrationError(MangoException error);
  }

  void execute(Callback callback, String baseURL, String clientId, String id, String registrationData);
}
