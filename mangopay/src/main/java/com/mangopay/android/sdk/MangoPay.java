package com.mangopay.android.sdk;


import android.content.Context;

import com.mangopay.android.sdk.model.ErrorCode;
import com.mangopay.android.sdk.model.MangoError;
import com.mangopay.android.sdk.util.TextUtil;

import java.util.Calendar;
import java.util.Date;

public class MangoPay {

  private static final String CARD_NUMBER_REGEX = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]" +
          "{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";
  private static final String CARD_EXP_REGEX = "^(0[1-9]|1[0-2])[1-9][0-9]$";
  private static final String CARD_CVV_REGEX = "^[0-9]{3,4}$";

  private static volatile MangoPay singleton = null;
  private Context context;

  private Callback callback;

  private String baseURL;
  private String clientId;
  private String cardPreregistrationId;

  private String cardRegistrationURL;
  private String preregistrationData;
  private String accessKey;

  private String cardNumber;
  private String expirationDate;
  private String cvx;

  MangoPay(Context context) {
    this.context = context;
  }

  /**
   * Begin the process creation with MangoPay SDK that will be tied to the given {@link android.content.Context}'s lifecycle and that uses the
   * given {@link Context}'s main thread.
   *
   * @param context - Android context
   */
  public static MangoPay with(Context context) {
    if (singleton == null) {
      synchronized (MangoPay.class) {
        if (singleton == null) {
          singleton = new MangoPay(context);
        }
      }
    }
    return singleton;
  }


  /**
   * @param callback Callback with the success or error value
   * @return This mango builder.
   */
  public MangoPay callback(Callback callback) {
    this.callback = callback;
    return this;
  }

  /**
   * Add the credit card number to the sdk
   *
   * @param cardNumber accepted inputs: 123412341234 or
   *                   1234 1234 1234 1234 or
   *                   1234-1234-1234-1234
   * @return This mango builder.
   */
  public MangoPay cardNumber(String cardNumber) {
    if (cardNumber != null) {
      this.cardNumber = cardNumber.replaceAll("[-\\s]", "");
    }
    return this;
  }

  /**
   * Add the credit card expiration date to the sdk
   *
   * @param expirationDate like MonthYear
   *                       e.g '0920' or
   *                       '11/20' or '02-19'
   * @return This mango builder.
   */
  public MangoPay cardExpirationDate(String expirationDate) {
    if (expirationDate != null) {
      this.expirationDate = expirationDate.replaceAll("[-/;\\s]", "");
    }
    return this;
  }

  /**
   * Add the credit card expiration month to the sdk
   *
   * @param month e.g 2 or 10
   * @return This mango builder.
   */
  public MangoPay cardExpirationMonth(int month) {
    if (month > 0) {
      this.expirationDate = (month <= 9 ? "0" + month : "" + month) +
              (this.expirationDate != null ? this.expirationDate : "");
    }
    return this;
  }

  /**
   * Add the credit card expiration month to the sdk
   *
   * @param year e.g 2019
   * @return This mango builder.
   */
  public MangoPay cardExpirationYear(int year) {
    if (year >= Calendar.getInstance().get(Calendar.YEAR)) {
      this.expirationDate = this.expirationDate != null ?
              this.expirationDate + (year % 100) : (year % 100) + "";
    }
    return this;
  }

  /**
   * Add the credit card expiration date to the sdk
   *
   * @param expirationDate future java date
   * @return This mango builder.
   */
  public MangoPay cardExpirationDate(Date expirationDate) {
    if (expirationDate != null) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(expirationDate);
      int month = cal.get(Calendar.MONTH) + 1;
      int year = cal.get(Calendar.YEAR);
      this.expirationDate = (month <= 9 ? "0" : "") +
              month + "" + (year % 100);
    }
    return this;
  }

  /**
   * Add the credit card expiration cvx to the sdk
   *
   * @param cvx e.g '123'
   * @return This mango builder.
   */
  public MangoPay cardCvx(String cvx) {
    this.cvx = cvx;
    return this;
  }

  /**
   * Add the card pre-registration baseUrl
   *
   * @param baseURL needed for the card registration request
   * @return This mango builder.
   */
  public MangoPay baseURL(String baseURL) {
    this.baseURL = baseURL;
    return this;
  }

  /**
   * Add the card pre-registration clientId
   *
   * @param clientId needed for the card registration request
   * @return This mango builder.
   */
  public MangoPay clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * Add the card pre-registration id
   *
   * @param cardPreregistrationId needed for the get token request
   * @return This mango builder.
   */
  public MangoPay cardPreregistrationId(String cardPreregistrationId) {
    this.cardPreregistrationId = cardPreregistrationId;
    return this;
  }

  /**
   * Add the card pre-registration url
   *
   * @param cardRegistrationURL needed for the get token request
   * @return This mango builder.
   */
  public MangoPay cardRegistrationURL(String cardRegistrationURL) {
    this.cardRegistrationURL = cardRegistrationURL;
    return this;
  }

  /**
   * Add the card pre-registration data
   *
   * @param preregistrationData needed for the get token request
   * @return This mango builder.
   */
  public MangoPay preregistrationData(String preregistrationData) {
    this.preregistrationData = preregistrationData;
    return this;
  }

  /**
   * Add the card pre-registration accessKey
   *
   * @param accessKey needed for the get token request
   * @return This mango builder.
   */
  public MangoPay accessKey(String accessKey) {
    this.accessKey = accessKey;
    return this;
  }

  /**
   * Starts the card registration process
   */
  public MangoPaySDK start() {
    if (isValueValid(baseURL, "baseURL") && isValueValid(cardRegistrationURL, "cardRegistrationURL")
            && isValueValid(cardPreregistrationId, "cardPreregistrationId") && isValueValid(cvx, "cvx")
            && isValueValid(clientId, "clientId") && isValueValid(preregistrationData, "preregistrationData")
            && isValueValid(accessKey, "accessKey") && isValueValid(cardNumber, "cardNumber")
            && isValueValid(expirationDate, "expirationDate") && isCardNumberValid()
            && isCardExpirationValid() && isCardCvxValid()) {
      return new MangoPaySDK(callback, baseURL, clientId,
              cardPreregistrationId, cardRegistrationURL,
              preregistrationData, accessKey, cardNumber,
              expirationDate, cvx, context);
    } else {
      return null;
    }
  }

  private boolean isCardExpirationValid() {
    if (expirationDate != null && expirationDate.matches(CARD_EXP_REGEX)) {
      return true;
    } else {
      if (callback != null) {
        callback.failure(new MangoError(ErrorCode.EXPIRY_DATE_FORMAT_ERROR.getValue(),
                "Invalid card expiration date.", ErrorCode.VALIDATION.getValue()));
      }
      throw new IllegalStateException("Invalid card expiration date.");
    }
  }

  private boolean isCardNumberValid() {
    if (cardNumber != null && cardNumber.matches(CARD_NUMBER_REGEX)) {
      return true;
    } else {
      if (callback != null) {
        callback.failure(new MangoError(ErrorCode.CARD_NUMBER_FORMAT_ERROR.getValue(),
                "Invalid card number.", ErrorCode.VALIDATION.getValue()));
      }
      throw new IllegalStateException("Invalid card number.");
    }
  }

  private boolean isCardCvxValid() {
    if (cvx != null && cvx.matches(CARD_CVV_REGEX)) {
      return true;
    } else {
      if (callback != null) {
        callback.failure(new MangoError(ErrorCode.CVV_FORMAT_ERROR.getValue(),
                "Invalid card cvv.", ErrorCode.VALIDATION.getValue()));
      }
      throw new IllegalStateException("Invalid card cvv.");
    }
  }

  private boolean isValueValid(String value, String param) {
    if (!TextUtil.isBlank(value)) {
      return true;
    } else {
      if (callback != null) {
        callback.failure(new MangoError(ErrorCode.MISSING_FIELD_ERROR.getValue(),
                "Missing field: " + param, ErrorCode.VALIDATION.getValue()));
      }
      throw new IllegalStateException("Missing field: " + param);
    }
  }

}
