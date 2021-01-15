package com.mangopay.android.example;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mangopay.android.sdk.Callback;
import com.mangopay.android.sdk.MangoPay;
import com.mangopay.android.sdk.model.CardRegistration;
import com.mangopay.android.sdk.model.MangoCard;
import com.mangopay.android.sdk.model.MangoSettings;
import com.mangopay.android.sdk.model.exception.MangoException;
import com.mangopay.android.sdk.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static java.util.concurrent.TimeUnit.SECONDS;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCardRegistration();
    }

    /*
     * Get card pre-registration data needed for card registration
     * */
    private void getCardRegistration() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    //Put the address for your server
                    String url = "http://10.0.2.2:3000/cardRegistrations/kit";

                    URL obj = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

                    connection.setConnectTimeout((int) SECONDS.toMillis(30));
                    connection.setReadTimeout((int) SECONDS.toMillis(30));

                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setDoOutput(true);
                    JSONObject json = new JSONObject();
                    json.put("UserId","96980078");
                    json.put("Currency", "EUR");
                    OutputStream os = connection.getOutputStream();
                    try {
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        osw.write(json.toString());
                        osw.flush();
                        osw.close();
                    } catch (Exception exception) {
                        Log.e(TAG, exception.getMessage());
                        return null;
                    } finally {
                        os.close();
                    }

                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_CREATED) {
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
                } catch (IOException | JSONException e) {
                    Log.e(TAG, e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                if (response != null && response.length() > 0) {
                    try {
                        JSONObject object = new JSONObject(response);
                        String accessKey = JsonUtil.getValue(object, "accessKey");
                        String baseURL = JsonUtil.getValue(object, "baseURL");
                        String cardPreregistrationId = JsonUtil.getValue(object, "cardPreregistrationId");
                        String cardRegistrationURL = JsonUtil.getValue(object, "cardRegistrationURL");
                        String clientId = JsonUtil.getValue(object, "clientId");
                        String preregistrationData = JsonUtil.getValue(object, "preregistrationData");

                        MangoSettings mSettings = new MangoSettings(baseURL, clientId, cardPreregistrationId,
                                cardRegistrationURL, preregistrationData, accessKey);
                        MangoCard mCard = new MangoCard("3569990000000157", "0920", "123");

                        MangoPay mangopay = new MangoPay(MainActivity.this, mSettings);

                        mangopay.registerCard(mCard, new Callback() {
                            @Override
                            public void success(CardRegistration cardRegistration) {
                                Toast.makeText(MainActivity.this, cardRegistration.toString(), Toast.LENGTH_SHORT).show();
                                Log.d(MainActivity.class.getSimpleName(), cardRegistration.toString());
                            }

                            @Override
                            public void failure(MangoException error) {
                                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

            /*

            MangoPayBuilder builder = new MangoPayBuilder(MainActivity.this);
            builder.baseURL(baseURL)
                    .clientId(clientId).accessKey(accessKey)
                    .cardRegistrationURL(cardRegistrationURL)
                    .preregistrationData(preregistrationData)
                    .cardPreregistrationId(cardPreregistrationId)
                    .cardNumber("3569990000000157")
                     //.cardExpirationDate("0920")
                    .cardExpirationYear(2017)
                    .cardExpirationMonth(2)
                    .cardCvx("123")
                    .logLevel(LogLevel.FULL)
                    .callback(new Callback() {
                      @Override public void success(CardRegistration cardRegistration) {
                        Log.d(MainActivity.class.getSimpleName(), cardRegistration.toString());
                      }

                      @Override public void failure(MangoException error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                      }
                    }).start();
              */

                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }.execute();
    }
}
