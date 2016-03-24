package com.mangopay.android.sdk.domain.api;

import com.mangopay.android.sdk.model.CardRegistration;

public interface CardRegistrationInteractor {
  interface Callback {
    void onCardRegistrationSuccess(CardRegistration response);

    void onCardRegistrationError(String message);
  }

  void execute(Callback callback, String baseURL, String clientId, String id, String registrationData);
}
