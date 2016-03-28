package com.mangopay.android.sdk.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.List;

public final class TextUtil {

  private TextUtil() {
  }

  public static boolean isBlank(String value) {
    return value == null || value.trim().length() == 0;
  }

  public static String getValue(JSONObject json, String key) throws JSONException {
    if (json.has(key) && !json.isNull(key)) {
      return json.getString(key);
    }
    return null;
  }

  public static String getQuery(List<AbstractMap.SimpleEntry<String, String>> params) throws UnsupportedEncodingException {
    StringBuilder result = new StringBuilder();
    boolean first = true;

    for (AbstractMap.SimpleEntry pair : params) {
      if (first)
        first = false;
      else
        result.append("&");

      result.append(URLEncoder.encode(pair.getKey().toString(), "UTF-8"));
      result.append("=");
      result.append(URLEncoder.encode(pair.getValue().toString(), "UTF-8"));
    }

    return result.toString();
  }

}
