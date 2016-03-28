package com.mangopay.android.sdk.model;

/**
 * The  PayInDirectCard object lets you pay with a registered Card (token). Here are the steps to follow:
 * Complete the CardRegistration steps (From 1. to 9. in the diagram)
 * Create a « Direct PayIn » object with the « CardId » received through the CardRegistration
 */
public class CardRegistration {
  // Card registration id
  private String id;
  // Custom data
  private String tag;
  // The creation date of the object
  private long creationDate;
  // ID of the credited user (owner of the credited wallet)
  private String userId;
  private String accessKey;
  private String preregistrationData;
  private String registrationData;
  // The ID of the registered card (Got through CardRegistration object)
  private String cardId;
  private String cardType;
  private String cardRegistrationURL;
  /*
  * The status of the payment:
  * « CREATED » (the object is created),
  * « SUCCEEDED » (the payment is succeeded),
  * « FAILED » (the payment has failed).
  */
  private String status;
  // The transaction result code
  private String resultCode;
  // The transaction result Message
  private String resultMessage;
  private String currency;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(long creationDate) {
    this.creationDate = creationDate;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String creditedUserId) {
    this.userId = creditedUserId;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getPreregistrationData() {
    return preregistrationData;
  }

  public void setPreregistrationData(String preregistrationData) {
    this.preregistrationData = preregistrationData;
  }

  public String getRegistrationData() {
    return registrationData;
  }

  public void setRegistrationData(String registrationData) {
    this.registrationData = registrationData;
  }

  public String getCardId() {
    return cardId;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public String getCardRegistrationURL() {
    return cardRegistrationURL;
  }

  public void setCardRegistrationURL(String cardRegistrationURL) {
    this.cardRegistrationURL = cardRegistrationURL;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getResultCode() {
    return resultCode;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public String getResultMessage() {
    return resultMessage;
  }

  public void setResultMessage(String resultMessage) {
    this.resultMessage = resultMessage;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CardRegistration that = (CardRegistration) o;

    if (creationDate != that.creationDate) return false;
    if (!id.equals(that.id)) return false;
    if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
    return !(cardId != null ? !cardId.equals(that.cardId) : that.cardId != null);

  }

  @Override public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + (int) (creationDate ^ (creationDate >>> 32));
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    result = 31 * result + (cardId != null ? cardId.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "CardRegistration{" +
            "id='" + id + '\'' +
            ", tag='" + tag + '\'' +
            ", creationDate=" + creationDate +
            ", userId='" + userId + '\'' +
            ", accessKey='" + accessKey + '\'' +
            ", preregistrationData='" + preregistrationData + '\'' +
            ", registrationData='" + registrationData + '\'' +
            ", cardId='" + cardId + '\'' +
            ", cardType='" + cardType + '\'' +
            ", cardRegistrationURL='" + cardRegistrationURL + '\'' +
            ", status='" + status + '\'' +
            ", resultCode='" + resultCode + '\'' +
            ", resultMessage='" + resultMessage + '\'' +
            ", currency='" + currency + '\'' +
            '}';
  }
}
