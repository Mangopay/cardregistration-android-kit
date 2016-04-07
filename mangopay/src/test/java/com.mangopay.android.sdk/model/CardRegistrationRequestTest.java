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
public class CardRegistrationRequestTest {

  private static final String ID = "id";
  private static final String REG_DATA = "regData";

  @Test
  public void shouldBeValidatedOk() {
    CardRegistrationRequest request = new CardRegistrationRequest(ID, REG_DATA);
    assertNotNull(request);
    assertEquals(ID, request.getId());
    assertEquals(REG_DATA, request.getRegistrationData());
  }

  @Test
  public void shouldGetCorrectField() {
    CardRegistrationRequest request = new CardRegistrationRequest(ID, REG_DATA);
    List<AbstractMap.SimpleEntry<String, String>> fields = request.getFields(null);

    assertTrue(fields.size() == 2);
    assertEquals(ID, fields.get(0).getValue());
    assertEquals(REG_DATA, fields.get(1).getValue());
  }
}
