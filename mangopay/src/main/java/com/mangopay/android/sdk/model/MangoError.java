package com.mangopay.android.sdk.model;

import java.util.Date;

/**
 * Possible error returned from the mangoPay servers
 */
public class MangoError {
  private String id;
  private String message;
  private String type;
  private long date;

  public MangoError() {
  }

  public MangoError(String id, String message) {
    this(id, message, null);
  }

  public MangoError(String id, String message, String type) {
    this.id = id;
    this.message = message;
    this.type = type;
    this.date = new Date().getTime();
  }

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

  @Override public String toString() {
    return "MangoError{" +
            "id='" + id + '\'' +
            ", message='" + message + '\'' +
            ", type='" + type + '\'' +
            ", date=" + date +
            '}';
  }
}
