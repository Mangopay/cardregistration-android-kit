package com.mangopay.android.sdk.model;

/**
 * Model used as BODY on the card registration request.
 * Any field added here will also be added on the body of the request.
 */
public class CardRegistrationRequest extends RequestObject {

  private String Id;
  private String RegistrationData;

  public CardRegistrationRequest(String id, String registrationData) {
    Id = id;
    RegistrationData = registrationData;
  }

  public String getId() {
    return Id;
  }

  public String getRegistrationData() {
    return RegistrationData;
  }

}
