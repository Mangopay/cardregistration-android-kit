package com.mangopay.android.sdk.model;

import com.mangopay.android.sdk.model.exception.MangoException;
import com.mangopay.android.sdk.util.PrintLog;

/**
 * This model holds the card information
 */
public class MangoCard {

  private static final String CARD_NUMBER_REGEX = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]" +
          "{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";
  private static final String CARD_EXP_REGEX = "^(0[1-9]|1[0-2])[1-9][0-9]$";
  private static final String CARD_CVV_REGEX = "^[0-9]{3,4}$";

  private String cardNumber;
  private String expirationDate;
  private String cvx;

  public MangoCard(String cardNumber, String expirationDate, String cvx) {
    this.cardNumber = cardNumber;
    this.expirationDate = expirationDate;
    this.cvx = cvx;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public String getExpirationDate() {
    return expirationDate;
  }

  public String getCvx() {
    return cvx;
  }

  public void validate() throws MangoException {
    validateCardExpiration();
    validateCardNumber();
    validateCardCvx();
  }

  private void validateCardExpiration() {
    if (!isFieldValid(expirationDate, CARD_EXP_REGEX)) {
      MangoException error = new MangoException(ErrorCode.EXPIRY_DATE_FORMAT_ERROR.getValue(),
              "Invalid card expiration date.", ErrorCode.VALIDATION.getValue());
      PrintLog.error(error.toString());
      throw error;
    }
  }

  private void validateCardNumber() {
    if (!isFieldValid(cardNumber, CARD_NUMBER_REGEX)) {
      MangoException error = new MangoException(ErrorCode.CARD_NUMBER_FORMAT_ERROR.getValue(),
              "Invalid card number.", ErrorCode.VALIDATION.getValue());
      PrintLog.error(error.toString());
      throw error;
    }
  }

  private void validateCardCvx() {
    if (!isFieldValid(cvx, CARD_CVV_REGEX)) {
      MangoException error = new MangoException(ErrorCode.CVV_FORMAT_ERROR.getValue(),
              "Invalid card cvv.", ErrorCode.VALIDATION.getValue());
      PrintLog.error(error.toString());
      throw error;
    }
  }

  private boolean isFieldValid(String field, String regex) {
    return field != null && field.matches(regex);
  }
}
