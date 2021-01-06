package com.mangopay.android.sdk.domain.service;

import com.mangopay.android.sdk.model.ErrorCode;
import com.mangopay.android.sdk.model.exception.MangoException;
import com.mangopay.android.sdk.util.JsonUtil;
import com.mangopay.android.sdk.util.PrintLog;
import com.mangopay.android.sdk.util.TextUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.AbstractMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


/**
 * Simple service implementation
 */
public class CardServiceImpl implements CardService {

  @SuppressWarnings("unchecked")
  @Override public void post(String mURL, List<AbstractMap.SimpleEntry<String, String>> params,
                             String acceptHeader, ServiceCallback callback) {
    try {
      URL obj = new URL(mURL);
      HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
      connection.setRequestProperty("Accept", acceptHeader);
      connection.setDoInput(true);
      connection.setDoOutput(true);

      PrintLog.debug("Started get token request\n" + connection.getRequestMethod() + ": " + mURL);

      OutputStream os = connection.getOutputStream();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
      writer.write(TextUtil.getQuery(params));
      writer.flush();
      writer.close();
      os.close();

      connection.connect();

      int responseCode = connection.getResponseCode();
      StringBuilder response = new StringBuilder();
      if (responseCode == HttpsURLConnection.HTTP_OK) {
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((line = br.readLine()) != null) {
          response.append(line);
        }
        callback.success(response.toString());
      } else {
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        while ((line = br.readLine()) != null) {
          response.append(line);
        }

        MangoException error = JsonUtil.getMangoError(callback, response.toString());
        if (error != null) {
          if (error.getMessage() == null) {
            error = new MangoException(ErrorCode.SERVER_ERROR.getValue(),
                    connection.getResponseMessage());
          }
          callback.failure(error);
        }
      }
      connection.disconnect();
    } catch (IOException e) {
      callback.failure(new MangoException(e));
      PrintLog.error(e.getMessage());
    }
  }
}
