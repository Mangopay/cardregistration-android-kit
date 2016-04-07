package com.mangopay.android.sdk.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.AbstractMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CreateTokenRequestTest {

  private static final String DATA = "data";
  private static final String ACCESS_KEY = "accessKey";
  private static final String CARD_NUMBER = "3569990000000157";
  private static final String CARD_EXPIRATION_DATE = "0920";
  private static final String CARD_CVX = "123";

  @Test
  public void shouldBeValidatedOk() {
    CreateTokenRequest request = new CreateTokenRequest(DATA, ACCESS_KEY,
            CARD_NUMBER, CARD_EXPIRATION_DATE, CARD_CVX);
    assertNotNull(request);
    assertEquals(DATA, request.getData());
    assertEquals(ACCESS_KEY, request.getAccessKeyRef());
    assertEquals(CARD_NUMBER, request.getCardNumber());
    assertEquals(CARD_EXPIRATION_DATE, request.getCardExpirationDate());
    assertEquals(CARD_CVX, request.getCardCvx());
  }

  @Test
  public void shouldGetCorrectField() {
    CreateTokenRequest request = new CreateTokenRequest(DATA, ACCESS_KEY,
            CARD_NUMBER, CARD_EXPIRATION_DATE, CARD_CVX);
    List<AbstractMap.SimpleEntry<String, String>> fields = request.getFields(null);

    assertTrue(fields.size() == 5);
    assertEquals(DATA, fields.get(0).getValue());
    assertEquals(ACCESS_KEY, fields.get(1).getValue());
    assertEquals(CARD_NUMBER, fields.get(2).getValue());
    assertEquals(CARD_EXPIRATION_DATE, fields.get(3).getValue());
    assertEquals(CARD_CVX, fields.get(4).getValue());
  }
}
