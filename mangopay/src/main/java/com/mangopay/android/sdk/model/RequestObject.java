package com.mangopay.android.sdk.model;

import com.mangopay.android.sdk.domain.service.ServiceCallback;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class RequestObject {

  /**
   * Using reflection we are returning a List of SimpleEntry objects generated
   * from our fields, used on our API requests
   */
  public List<AbstractMap.SimpleEntry<String, String>> getFields(ServiceCallback callback) {
    Field[] declaredFields = this.getClass().getDeclaredFields();
    List<AbstractMap.SimpleEntry<String, String>> params = new ArrayList<>(declaredFields.length);
    for (Field field : declaredFields) {
      try {
        field.setAccessible(true);
        String value = (String) field.get(this);
        params.add(new AbstractMap.SimpleEntry<>(field.getName(), value));
      } catch (IllegalAccessException e) {
        callback.failure(new MangoError(ErrorCode.SDK_ERROR.getValue(), e.getMessage()));
      }
    }
    return params;
  }
}
