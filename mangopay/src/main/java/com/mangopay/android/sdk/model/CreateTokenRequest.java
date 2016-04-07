package com.mangopay.android.sdk.model;

/**
 * Model used as BODY on the get token request.
 * Any field added here will also be added on the body of the request.
 */
public class CreateTokenRequest extends RequestObject {

  private String data;
  private String accessKeyRef;
  private String cardNumber;
  private String cardExpirationDate;
  private String cardCvx;

  public CreateTokenRequest(String data, String accessKeyRef, String cardNumber,
                            String cardExpirationDate, String cardCvx) {
    this.data = data;
    this.accessKeyRef = accessKeyRef;
    this.cardNumber = cardNumber;
    this.cardExpirationDate = cardExpirationDate;
    this.cardCvx = cardCvx;
  }
}
