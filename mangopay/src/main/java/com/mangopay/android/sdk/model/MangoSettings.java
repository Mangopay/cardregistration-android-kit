package com.mangopay.android.sdk.model;

import com.mangopay.android.sdk.model.exception.MangoError;
import com.mangopay.android.sdk.util.PrintLog;
import com.mangopay.android.sdk.util.TextUtil;

import java.lang.reflect.Field;

/**
 * This model holds the card registration data
 */
public class MangoSettings {

  private String baseURL;
  private String clientId;
  private String cardPreregistrationId;

  private String cardRegistrationURL;
  private String preregistrationData;
  private String accessKey;

  public MangoSettings(String baseURL, String clientId, String cardPreregistrationId,
                       String cardRegistrationURL, String preregistrationData, String accessKey) {
    this.baseURL = baseURL;
    this.clientId = clientId;
    this.cardPreregistrationId = cardPreregistrationId;
    this.cardRegistrationURL = cardRegistrationURL;
    this.preregistrationData = preregistrationData;
    this.accessKey = accessKey;
  }

  public String getBaseURL() {
    return baseURL;
  }

  public String getClientId() {
    return clientId;
  }

  public String getCardPreregistrationId() {
    return cardPreregistrationId;
  }

  public String getCardRegistrationURL() {
    return cardRegistrationURL;
  }

  public String getPreregistrationData() {
    return preregistrationData;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void validate() throws MangoError {
    try {
      Field[] fields = this.getClass().getDeclaredFields();
      for (Field field : fields) {
        String value = (String) field.get(this);
        if (TextUtil.isBlank(value)) {
          MangoError error = new MangoError(ErrorCode.MISSING_FIELD_ERROR.getValue(),
                  "Missing field: " + field.getName(), ErrorCode.VALIDATION.getValue());
          PrintLog.error(error.toString());
          throw error;
        }
      }
    } catch (IllegalAccessException e) {
      throw new MangoError(e);
    }
  }
}
