package com.mangopay.android.sdk.domain;

import com.mangopay.android.sdk.domain.api.GetTokenInteractor;
import com.mangopay.android.sdk.executor.Executor;
import com.mangopay.android.sdk.executor.Interactor;
import com.mangopay.android.sdk.executor.MainThread;
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
 * Get Token request implementation
 */
public class GetTokenInteractorImpl implements Interactor, GetTokenInteractor {

  private final Executor mExecutor;
  private final MainThread mMainThread;
  private String mRegistrationURL;
  private String mPreData;
  private String mAccessKey;
  private String mCardNumber;
  private String mExpirationDate;
  private String mCardCvx;

  private Callback mCallback;

  public GetTokenInteractorImpl(Executor executor, MainThread mainThread) {
    this.mExecutor = executor;
    this.mMainThread = mainThread;
  }

  @Override public void execute(Callback callback, String cardRegistrationURL,
                                String preregistrationData, String accessKeyRef,
                                String cardNumber, String cardExpirationDate, String cardCvx) {
    this.mCallback = callback;
    this.mRegistrationURL = cardRegistrationURL;
    this.mPreData = preregistrationData;
    this.mAccessKey = accessKeyRef;
    this.mCardNumber = cardNumber;
    this.mExpirationDate = cardExpirationDate;
    this.mCardCvx = cardCvx;
    this.mExecutor.run(this);
  }

  @Override public void run() {
    try {
      URL obj = new URL(mRegistrationURL);
      HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
      connection.setRequestProperty("Accept", "text/plain");
      connection.setDoInput(true);
      connection.setDoOutput(true);

      List<AbstractMap.SimpleEntry<String, String>> params = new ArrayList<>();
      params.add(new AbstractMap.SimpleEntry<>("data", mPreData));
      params.add(new AbstractMap.SimpleEntry<>("accessKeyRef", mAccessKey));
      params.add(new AbstractMap.SimpleEntry<>("cardNumber", mCardNumber));
      params.add(new AbstractMap.SimpleEntry<>("cardExpirationDate", mExpirationDate));
      params.add(new AbstractMap.SimpleEntry<>("cardCvx", mCardCvx));

      PrintLog.debug("Started get token request\n" + connection.getRequestMethod() + ": " + mRegistrationURL);

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
        notifySuccess(response);
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
          error.setDate((Long) json.get("Date"));
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

  private void notifySuccess(final String response) {
    mMainThread.post(new Runnable() {
      @Override public void run() {
        mCallback.onGetTokenSuccess(response);
      }
    });
  }

  private void notifyError(final MangoError message) {
    mMainThread.post(new Runnable() {
      @Override public void run() {
        mCallback.onGetTokenError(message);
      }
    });
  }
}
