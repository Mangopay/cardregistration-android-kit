package com.mangopay.android.sdk;

import android.content.Context;

import com.mangopay.android.sdk.model.LogLevel;
import com.mangopay.android.sdk.model.MangoCard;
import com.mangopay.android.sdk.util.PrintLog;

import java.util.Calendar;
import java.util.Date;

/**
 * Fluent API for creating {@link MangoPay} instances.
 */
public class MangoPayBuilder {

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

  public MangoPayBuilder(Context context) {
    this.context = context;
  }

  /**
   * @param callback Callback with the success or error value
   * @return This mango builder.
   */
  public MangoPayBuilder callback(Callback callback) {
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
  public MangoPayBuilder cardNumber(String cardNumber) {
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
  public MangoPayBuilder cardExpirationDate(String expirationDate) {
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
  public MangoPayBuilder cardExpirationMonth(int month) {
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
  public MangoPayBuilder cardExpirationYear(int year) {
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
  public MangoPayBuilder cardExpirationDate(Date expirationDate) {
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
  public MangoPayBuilder cardCvx(String cvx) {
    this.cvx = cvx;
    return this;
  }

  /**
   * Add the card pre-registration baseUrl
   *
   * @param baseURL needed for the card registration request
   * @return This mango builder.
   */
  public MangoPayBuilder baseURL(String baseURL) {
    this.baseURL = baseURL;
    return this;
  }

  /**
   * Add the card pre-registration clientId
   *
   * @param clientId needed for the card registration request
   * @return This mango builder.
   */
  public MangoPayBuilder clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  /**
   * Add the card pre-registration id
   *
   * @param cardPreregistrationId needed for the get token request
   * @return This mango builder.
   */
  public MangoPayBuilder cardPreregistrationId(String cardPreregistrationId) {
    this.cardPreregistrationId = cardPreregistrationId;
    return this;
  }

  /**
   * Add the card pre-registration url
   *
   * @param cardRegistrationURL needed for the get token request
   * @return This mango builder.
   */
  public MangoPayBuilder cardRegistrationURL(String cardRegistrationURL) {
    this.cardRegistrationURL = cardRegistrationURL;
    return this;
  }

  /**
   * Add the card pre-registration data
   *
   * @param preregistrationData needed for the get token request
   * @return This mango builder.
   */
  public MangoPayBuilder preregistrationData(String preregistrationData) {
    this.preregistrationData = preregistrationData;
    return this;
  }

  /**
   * Add the card pre-registration accessKey
   *
   * @param accessKey needed for the get token request
   * @return This mango builder.
   */
  public MangoPayBuilder accessKey(String accessKey) {
    this.accessKey = accessKey;
    return this;
  }

  /**
   * Set the SDK Log Level e.g 'NONE' or 'FULL' by default is set to 'NONE'
   *
   * @param logLevel needed for logger
   * @return This mango builder.
   */
  public MangoPayBuilder logLevel(LogLevel logLevel) {
    PrintLog.setLogLevel(logLevel);
    return this;
  }

  public void start() {
    MangoCard mCard = new MangoCard(cardNumber, expirationDate, cvx);
    build().registerCard(mCard, callback);
  }

  public MangoPay build() {
    PrintLog.debug("MangoPay SDK initialised");
    return new MangoPay(context, baseURL, clientId, cardPreregistrationId, cardRegistrationURL,
            preregistrationData, accessKey, cvx, cardNumber, expirationDate, callback);
  }

}
