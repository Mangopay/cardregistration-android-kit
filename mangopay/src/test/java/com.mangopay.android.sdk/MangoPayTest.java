package com.mangopay.android.sdk;

import android.content.Context;

import com.mangopay.android.sdk.model.CardRegistration;
import com.mangopay.android.sdk.model.ErrorCode;
import com.mangopay.android.sdk.model.MangoCard;
import com.mangopay.android.sdk.model.MangoSettings;
import com.mangopay.android.sdk.model.exception.MangoException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MangoPayTest {

  private static final String BASE_URL = "https://www.mangopay.com/";
  private static final String CLIENT_ID = "123";
  private static final String CARD_PRE_REG_ID = "preId123";
  private static final String CARD_REG_URL = "cardRegUrl";
  private static final String PRE_REG_DATA = "preRegData";
  private static final String ACCESS_KEY = "accessKey";

  private static final String CARD_NUMBER = "3569990000000157";
  private static final String CARD_EXPIRATION_DATE = "0920";
  private static final String CARD_CVX = "123";

  private static final String INVALID = "";

  @Mock Context context;

  MangoSettings settings;
  MangoCard card;

  @Before
  public void setUp() {
    settings = new MangoSettings(BASE_URL, CLIENT_ID, CARD_PRE_REG_ID, CARD_REG_URL, PRE_REG_DATA, ACCESS_KEY);
    card = new MangoCard(CARD_NUMBER, CARD_EXPIRATION_DATE, CARD_CVX);
  }

  @Test
  public void shouldTest() {
    MangoPay mangoPay = new MangoPay(context, settings);
    assertNotNull(mangoPay);
  }

  @Test
  public void shouldCatchSettingsValidationExceptionOnTheCallback() {
    settings = new MangoSettings(INVALID, INVALID, INVALID, INVALID, INVALID, INVALID);
    MangoPay mangoPay = new MangoPay(context, settings);
    mangoPay.registerCard(card, new Callback() {
      @Override public void success(CardRegistration jsonResponse) {

      }

      @Override public void failure(MangoException error) {
        assertNotNull(error);
        assertEquals(ErrorCode.MISSING_FIELD_ERROR.getValue(), error.getId());
        assertEquals(ErrorCode.VALIDATION.getValue(), error.getType());
      }
    });
  }

  @Test
  public void shouldCatchCardValidationExceptionOnTheCallback() {
    MangoPay mangoPay = new MangoPay(context, settings);
    card = new MangoCard(INVALID, INVALID, INVALID);
    mangoPay.registerCard(card, new Callback() {
      @Override public void success(CardRegistration jsonResponse) {

      }

      @Override public void failure(MangoException error) {
        assertNotNull(error);
        assertEquals(ErrorCode.VALIDATION.getValue(), error.getType());
      }
    });
  }
}
