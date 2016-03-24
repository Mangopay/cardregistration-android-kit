package com.mangopay.android.example;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mangopay.android.sdk.Callback;
import com.mangopay.android.sdk.MangoPay;
import com.mangopay.android.sdk.model.CardRegistration;
import com.mangopay.android.sdk.util.TextUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getCardRegistration();
  }

  /*
  * Get card pre-registration data needed for card registration
  * */
  private void getCardRegistration() {
    new AsyncTask<Void, Void, String>() {
      @Override protected String doInBackground(Void... voids) {
        try {
          String url = "http://demo-mangopay.rhcloud.com/card-registration";

          URL obj = new URL(url);
          HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

          // optional default is GET
          connection.setRequestMethod("GET");
          connection.setRequestProperty("Accept", "application/json");

          connection.connect();

          int responseCode = connection.getResponseCode();
          if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
              response.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return response.toString();
          } else {
            connection.disconnect();
            return "";
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
        return null;
      }

      @Override protected void onPostExecute(String response) {
        super.onPostExecute(response);
        if (response != null && response.length() > 0) {
          try {
            JSONObject object = new JSONObject(response);
            String accessKey = TextUtil.getValue(object, "accessKey");
            String baseURL = TextUtil.getValue(object, "baseURL");
            String cardPreregistrationId = TextUtil.getValue(object, "cardPreregistrationId");
            String cardRegistrationURL = TextUtil.getValue(object, "cardRegistrationURL");
            String clientId = TextUtil.getValue(object, "clientId");
            String preregistrationData = TextUtil.getValue(object, "preregistrationData");

            MangoPay.with(MainActivity.this).baseURL(baseURL)
                    .clientId(clientId).accessKey(accessKey)
                    .cardRegistrationURL(cardRegistrationURL)
                    .preregistrationData(preregistrationData)
                    .cardPreregistrationId(cardPreregistrationId)
                    .cardNumber("3569990000000157")
                    .cardExpirationDate("0920")
                    .cardCvx("123")
                    .callback(new Callback() {
                      @Override public void success(CardRegistration cardRegistration) {
                        Log.d(MainActivity.class.getSimpleName(), cardRegistration.toString());
                      }

                      @Override public void failure(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                      }
                    }).start();
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
      }
    }.execute();
  }
}
