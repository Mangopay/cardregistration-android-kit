package com.mangopay.android.sdk.model.exception;

import com.mangopay.android.sdk.model.ErrorCode;

/**
 * Possible error returned from the mangoPay servers
 */
public class MangoError extends RuntimeException {

  private String id;
  private String type;
  private long timestamp;

  public MangoError(Throwable throwable) {
    super(throwable);
    this.id = ErrorCode.SDK_ERROR.getValue();
    this.timestamp = System.currentTimeMillis();
  }

  public MangoError(String id, String message) {
    this(id, message, null);
  }

  public MangoError(String id, String message, String type) {
    super(message);
    this.id = id;
    this.type = type;
    this.timestamp = System.currentTimeMillis();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
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
            ", message='" + getMessage() + '\'' +
            ", type='" + type + '\'' +
            ", timestamp=" + timestamp +
            '}';
  }
}
