package com.mangopay.android.sdk.model;


public enum ErrorCode {

  VALIDATION("sdk-validation"),
  SERVER_ERROR("server-error"),
  SDK_ERROR("sdk-error"),

  MISSING_FIELD_ERROR("105201"),
  CARD_NUMBER_FORMAT_ERROR("105202"),
  EXPIRY_DATE_FORMAT_ERROR("105203"),
  CVV_FORMAT_ERROR("105204");

  private String id;

  ErrorCode(String id) {
    this.id = id;
  }

  public String getValue() {
    return this.id;
  }
}
