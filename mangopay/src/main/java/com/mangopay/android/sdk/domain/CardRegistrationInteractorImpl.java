package com.mangopay.android.sdk.domain;

import com.mangopay.android.sdk.domain.api.CardRegistrationInteractor;
import com.mangopay.android.sdk.executor.Executor;
import com.mangopay.android.sdk.executor.Interactor;
import com.mangopay.android.sdk.executor.MainThread;
import com.mangopay.android.sdk.model.CardRegistration;
import com.mangopay.android.sdk.model.ErrorCode;
import com.mangopay.android.sdk.model.MangoError;
import com.mangopay.android.sdk.util.PrintLog;
import com.mangopay.android.sdk.util.TextUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Card registration request implementation
 */
public class CardRegistrationInteractorImpl implements Interactor, CardRegistrationInteractor {

  private final Executor mExecutor;
  private final MainThread mMainThread;
  private String mBaseUrl;
  private String mClientId;
  private String mId;
  private String mRegData;

  private Callback mCallback;

  public CardRegistrationInteractorImpl(Executor executor, MainThread mainThread) {
    this.mExecutor = executor;
    this.mMainThread = mainThread;
  }

  @Override public void execute(Callback callback, String baseURL, String clientId,
                                String id, String registrationData) {
    this.mCallback = callback;
    this.mBaseUrl = baseURL;
    this.mClientId = clientId;
    this.mId = id;
    this.mRegData = registrationData;
    this.mExecutor.run(this);
  }

  @Override public void run() {
    try {
      String url = mBaseUrl + "/v2/" + mClientId + "/CardRegistrations/" + mId;
      URL obj = new URL(url);
      HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
      connection.setRequestProperty("Accept", "application/json");
      connection.setDoInput(true);
      connection.setDoOutput(true);

      List<AbstractMap.SimpleEntry<String, String>> params = new ArrayList<>();
      params.add(new AbstractMap.SimpleEntry<>("Id", mId));
      params.add(new AbstractMap.SimpleEntry<>("RegistrationData", mRegData));

      PrintLog.debug("Started card registration request\n" + connection.getRequestMethod() + ": " + url);

      OutputStream os = connection.getOutputStream();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
      writer.write(TextUtil.getQuery(params));
      writer.flush();
      writer.close();
      os.close();

      connection.connect();

      int responseCode = connection.getResponseCode();
      String response = "";
      if (responseCode == HttpsURLConnection.HTTP_OK) {
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((line = br.readLine()) != null) {
          response += line;
        }
        JSONObject json = new JSONObject(response);

        CardRegistration cardRegistration = new CardRegistration();
        cardRegistration.setId(TextUtil.getValue(json, "Id"));
        cardRegistration.setTag(TextUtil.getValue(json, "Tag"));
        cardRegistration.setUserId(TextUtil.getValue(json, "UserId"));
        cardRegistration.setAccessKey(TextUtil.getValue(json, "AccessKey"));
        cardRegistration.setPreregistrationData(TextUtil.getValue(json, "PreregistrationData"));
        cardRegistration.setRegistrationData(TextUtil.getValue(json, "RegistrationData"));
        cardRegistration.setCardId(TextUtil.getValue(json, "CardId"));
        cardRegistration.setCardType(TextUtil.getValue(json, "CardType"));
        cardRegistration.setCardRegistrationURL(TextUtil.getValue(json, "CardRegistrationURL"));
        cardRegistration.setResultCode(TextUtil.getValue(json, "ResultCode"));
        cardRegistration.setResultMessage(TextUtil.getValue(json, "ResultMessage"));
        cardRegistration.setCurrency(TextUtil.getValue(json, "Currency"));
        cardRegistration.setStatus(TextUtil.getValue(json, "Status"));
        if (json.has("CreationDate") && !json.isNull("CreationDate")) {
          cardRegistration.setCreationDate(json.getLong("CreationDate"));
        }
        notifySuccess(cardRegistration);
      } else {
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        while ((line = br.readLine()) != null) {
          response += line;
        }
        JSONObject json = new JSONObject(response);
        MangoError error = new MangoError();
        error.setMessage(TextUtil.getValue(json, "Message"));
        error.setMessage(TextUtil.getValue(json, "Type"));
        error.setMessage(TextUtil.getValue(json, "Id"));
        if (json.has("Date") && !json.isNull("Date")) {
          error.setDate(json.getLong("Date"));
        }
        if (error.getMessage() == null) {
          error.setId(ErrorCode.SERVER_ERROR.getValue());
          error.setMessage(connection.getResponseMessage());
        }
        notifyError(error);
      }
      connection.disconnect();

    } catch (IOException | JSONException e) {
      MangoError error = new MangoError(ErrorCode.SDK_ERROR.getValue(), e.getMessage());
      PrintLog.error(error.toString());
      notifyError(error);
    }
  }

  private void notifySuccess(final CardRegistration response) {
    mMainThread.post(new Runnable() {
      @Override public void run() {
        mCallback.onCardRegistrationSuccess(response);
      }
    });
  }

  private void notifyError(final MangoError message) {
    mMainThread.post(new Runnable() {
      @Override public void run() {
        mCallback.onCardRegistrationError(message);
      }
    });
  }

}
