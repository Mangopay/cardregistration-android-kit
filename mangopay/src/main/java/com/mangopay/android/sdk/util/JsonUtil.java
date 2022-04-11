package com.mangopay.android.sdk.util;

import com.mangopay.android.sdk.domain.service.ServiceCallback;
import com.mangopay.android.sdk.model.CardRegistration;
import com.mangopay.android.sdk.model.exception.MangoException;

import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtil {

  private JsonUtil() {
  }

  public static String getValue(JSONObject json, String key) throws JSONException {
    if (json.has(key) && !json.isNull(key)) {
      return json.getString(key);
    }
    return null;
  }

  public static MangoException getMangoError(ServiceCallback callback, String jsonResponse) {
    try {
      JSONObject json = new JSONObject(jsonResponse);

      MangoException error = new MangoException(getValue(json, "Id"), getValue(json, "Message"), getValue(json, "Type"));
      if (json.has("Date") && !json.isNull("Date")) {
        error.setTimestamp(json.getLong("Date"));
      }
      return error;
    } catch (JSONException e) {
      callback.failure(new MangoException(e));
      PrintLog.error(e.getMessage());
    }
    return null;
  }

  public static CardRegistration getCardRegistration(ServiceCallback callback, String jsonResponse) {
    try {
      JSONObject json = new JSONObject(jsonResponse);

      CardRegistration cardRegistration = new CardRegistration();
      cardRegistration.setId(getValue(json, "Id"));
      cardRegistration.setTag(getValue(json, "Tag"));
      cardRegistration.setUserId(getValue(json, "UserId"));
      cardRegistration.setAccessKey(getValue(json, "AccessKey"));
      cardRegistration.setPreregistrationData(getValue(json, "PreregistrationData"));
      cardRegistration.setRegistrationData(getValue(json, "RegistrationData"));
      cardRegistration.setCardId(getValue(json, "CardId"));
      cardRegistration.setCardType(getValue(json, "CardType"));
      cardRegistration.setCardRegistrationURL(getValue(json, "CardRegistrationURL"));
      cardRegistration.setResultCode(getValue(json, "ResultCode"));
      cardRegistration.setResultMessage(getValue(json, "ResultMessage"));
      cardRegistration.setCurrency(getValue(json, "Currency"));
      cardRegistration.setStatus(getValue(json, "Status"));
      if (json.has("CreationDate") && !json.isNull("CreationDate")) {
        cardRegistration.setCreationDate(json.getLong("CreationDate"));
      }
      return cardRegistration;
    } catch (JSONException e) {
      callback.failure(new MangoException(e));
      PrintLog.error(e.getMessage());
    }
    return null;
  }


}
