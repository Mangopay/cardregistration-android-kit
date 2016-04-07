package com.mangopay.android.sdk.model;

import com.mangopay.android.sdk.model.exception.MangoException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MangoSettingsTest {

  private static final String BASE_URL = "https://www.mangopay.com/";
  private static final String CLIENT_ID = "123";
  private static final String CARD_PRE_REG_ID = "preId123";
  private static final String CARD_REG_URL = "cardRegUrl";
  private static final String PRE_REG_DATA = "preRegData";
  private static final String ACCESS_KEY = "accessKey";
  private static final String INVALID = "";

  @Test
  public void shouldBeValidatedOk() {
    MangoSettings settings = new MangoSettings(BASE_URL, CLIENT_ID,
            CARD_PRE_REG_ID, CARD_REG_URL, PRE_REG_DATA, ACCESS_KEY);
    try {
      settings.validate();
    } catch (MangoException e) {
      assertNotNull(e);
      assertEquals(ErrorCode.MISSING_FIELD_ERROR.getValue(), e.getId());
      assertEquals(ErrorCode.VALIDATION.getValue(), e.getType());
    }
    assertNotNull(settings);
    assertEquals(BASE_URL, settings.getBaseURL());
    assertEquals(CLIENT_ID, settings.getClientId());
    assertEquals(CARD_PRE_REG_ID, settings.getCardPreregistrationId());
    assertEquals(CARD_REG_URL, settings.getCardRegistrationURL());
    assertEquals(PRE_REG_DATA, settings.getPreregistrationData());
    assertEquals(ACCESS_KEY, settings.getAccessKey());
  }

  @Test(expected = MangoException.class)
  public void shouldThrowMissingBaseUrlValidationException() throws Exception {
    MangoSettings settings = new MangoSettings(INVALID, CLIENT_ID,
            CARD_PRE_REG_ID, CARD_REG_URL, PRE_REG_DATA, ACCESS_KEY);
    settings.validate();
  }

  @Test(expected = MangoException.class)
  public void shouldThrowMissingClientIdValidationException() throws Exception {
    MangoSettings settings = new MangoSettings(BASE_URL, INVALID,
            CARD_PRE_REG_ID, CARD_REG_URL, PRE_REG_DATA, ACCESS_KEY);
    settings.validate();
  }

  @Test(expected = MangoException.class)
  public void shouldThrowMissingCardPreRegIdValidationException() throws Exception {
    MangoSettings settings = new MangoSettings(BASE_URL, CLIENT_ID,
            INVALID, CARD_REG_URL, PRE_REG_DATA, ACCESS_KEY);
    settings.validate();
  }

  @Test(expected = MangoException.class)
  public void shouldThrowMissingRegUrlValidationException() throws Exception {
    MangoSettings settings = new MangoSettings(BASE_URL, CLIENT_ID,
            CARD_PRE_REG_ID, INVALID, PRE_REG_DATA, ACCESS_KEY);
    settings.validate();
  }

  @Test(expected = MangoException.class)
  public void shouldThrowMissingPreRegDataValidationException() throws Exception {
    MangoSettings settings = new MangoSettings(BASE_URL, CLIENT_ID,
            CARD_PRE_REG_ID, CARD_REG_URL, INVALID, ACCESS_KEY);
    settings.validate();
  }

  @Test(expected = MangoException.class)
  public void shouldThrowMissingAccessKeyValidationException() throws Exception {
    MangoSettings settings = new MangoSettings(INVALID, CLIENT_ID,
            CARD_PRE_REG_ID, CARD_REG_URL, PRE_REG_DATA, INVALID);
    settings.validate();
  }
}
