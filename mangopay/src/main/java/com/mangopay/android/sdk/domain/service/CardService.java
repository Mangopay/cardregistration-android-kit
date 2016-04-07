package com.mangopay.android.sdk.domain.service;

import java.util.AbstractMap;
import java.util.List;

/**
 * Simple service interface defining the http methods
 */
public interface CardService {

  void post(String url, List<AbstractMap.SimpleEntry<String, String>> params,
            String acceptHeader, ServiceCallback callback);
}
