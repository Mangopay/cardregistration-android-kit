package com.mangopay.android.sdk.model;

import com.mangopay.android.sdk.model.exception.MangoException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MangoCardTest {

  private static final String CARD_NUMBER = "3569990000000157";
  private static final String CARD_EXPIRATION_DATE = "0920";
  private static final String CARD_CVX = "123";
  private static final String INVALID = "";

  @Test
  public void shouldBeValidatedOk() {
    MangoCard card = new MangoCard(CARD_NUMBER, CARD_EXPIRATION_DATE, CARD_CVX);

    card.validate();

    assertNotNull(card);
    assertEquals(CARD_NUMBER, card.getCardNumber());
    assertEquals(CARD_EXPIRATION_DATE, card.getExpirationDate());
    assertEquals(CARD_CVX, card.getCvx());
  }

  @Test(expected = MangoException.class)
  public void shouldThrowCardNumberValidationException() throws Exception {
    MangoCard card = new MangoCard(INVALID, CARD_EXPIRATION_DATE, CARD_CVX);
    card.validate();
  }

  @Test(expected = MangoException.class)
  public void shouldThrowCardExpDateValidationException() throws Exception {
    MangoCard card = new MangoCard(CARD_NUMBER, INVALID, CARD_CVX);
    card.validate();
  }

  @Test(expected = MangoException.class)
  public void shouldThrowCardCvxValidationException() throws Exception {
    MangoCard card = new MangoCard(CARD_NUMBER, CARD_EXPIRATION_DATE, INVALID);
    card.validate();
  }
}
