package com.mangopay.android.sdk.model;

/**
 * Possible error returned from the mangoPay servers
 */
public class MangoError {
  private String id;
  private String message;
  private String type;
  private long date;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MangoError error = (MangoError) o;

    return !(id != null ? !id.equals(error.id) : error.id != null);

  }

  @Override public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
