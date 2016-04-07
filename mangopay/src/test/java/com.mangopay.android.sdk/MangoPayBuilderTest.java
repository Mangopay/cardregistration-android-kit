package com.mangopay.android.sdk;

import android.content.Context;

import com.mangopay.android.sdk.model.CardRegistration;
import com.mangopay.android.sdk.model.ErrorCode;
import com.mangopay.android.sdk.model.exception.MangoException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MangoPayBuilderTest {

  private static final String BASE_URL = "https://www.mangopay.com/";
  private static final String CARD_NUMBER = "3569990000000157";
  private static final String CARD_CVX = "123";

  private MangoPayBuilder builder;

  @Mock Context context;

  @Before
  public void setUp() {
    builder = new MangoPayBuilder(context);
  }

  @Test
  public void shouldCatchValidationExceptionOnTheCallback() {
    MangoPay mangopay = builder.baseURL(BASE_URL)
            .cardNumber(CARD_NUMBER).cardCvx(CARD_CVX)
            .callback(new Callback() {
              @Override public void success(CardRegistration jsonResponse) {

              }

              @Override public void failure(MangoException e) {
                assertNotNull(e);
                assertEquals(ErrorCode.MISSING_FIELD_ERROR.getValue(), e.getId());
                assertEquals(ErrorCode.VALIDATION.getValue(), e.getType());
              }
            }).build();

    assertNotNull(mangopay);

    mangopay.registerCard();
  }


}
