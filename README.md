# MANGOPAY Android card registration kit [![Build Status](https://travis-ci.org/Mangopay/cardregistration-android-kit.svg?branch=master)](https://travis-ci.org/Mangopay/cardregistration-android-kit)

The mangopay card registration library makes it easy to create a card registration object based on your credit card info.

##Installation
###Android Studio (or Gradle)

No need to clone the repository or download any files -- just add this line to your app's *build.gradle* inside the dependencies section:


```groovy
implementation 'com.mangopay.android.sdk:card-registration-library:1.0.1'
```

### Eclipse

1. Clone the repository.
2. Be sure you've installed the Android SDK with API Level 10+. This is the only requirement for development. Our bindings require the API Level 10 as a minimum at runtime which would work on any modern version of Android.
3. Import the *mangopay* folder into Eclipse (use "Existing Projects into Workspace", not "Existing Android Code").
4. In your project settings, add the *mangopay* project under the "Libraries" section of the "Android" category.

## Usage

### Important:
* Because the MANGOPAY Passphrase cannot be set in the application due to obviously security reasons, this requires an own server instance which has this sensitive information kept private. Using this library you are able to tokenize a card and send it to your server, and then you are able to charge the customer. The flow is described in [this diagram](https://docs.mangopay.com/api-references/payins/payindirectcard).
* The code examples below refer to the [demo app](/Mangopay/cardregistration-android-kit/tree/master/example) included in this repo - you can either use this or just create your own controller if you prefer

### Update your webapp
You should already have a webapp (the service on your server that communicates with your Android app) and you need to add this new card registration functionality - this includes the API call to MANGOPAY ([more info](https://docs.mangopay.com/api-references/card-registration/)). You will then provide the Android kit with the `url` to access this functionality ([configured here](https://github.com/Mangopay/cardregistration-android-kit/blob/master/example/src/main/java/com/mangopay/android/example/MainActivity.java#L45)). The `url` should return a JSON response (which has the information obtained from the MANGOPAY API) as follows:

```javascript
{
  "accessKey": "1X0m87dmM2LiwFgxPLBJ",
  "baseURL": "https://api.sandbox.mangopay.com",
  "cardPreregistrationId": "12444838",
  "cardRegistrationURL": "https://homologation-webpayment.payline.com/webpayment/getToken",
  "cardType": "CB_VISA_MASTERCARD",
  "clientId": "sdk-unit-tests",
  "preregistrationData": "ObMObfSdwRfyE4QClGtUc6um8zvFYamY_t-LNSwKAxBisfd7z3cTgS83cCwyP9Gp7qGR3aNxrLUiPbx-Z--VxQ"
}
```

### Mangopay SDK creation
Using the information received from your server, create an instance of MangoPay as follows:

```java
// holds the card registration data
MangoSettings mSettings = new MangoSettings(baseURL, clientId, cardPreregistrationId,cardRegistrationURL, preregistrationData, accessKey);
             
// using the default constructor where you should pass the android context and  the settings object
MangoPay mangopay = new MangoPay(this, mSettings);

// or using the mangopay builder
MangoPay mangopay = new MangoPayBuilder(this).build();
```

### Register card information

```java       
// holds the card information
MangoCard mCard = new MangoCard("3569990000000157", "0920", "123");

// register card method with callback
mangopay.registerCard(mCard, new Callback() {
     @Override public void success(CardRegistration cardRegistration) {
       Log.d(MainActivity.class.getSimpleName(), cardRegistration.toString());
     }
     @Override public void failure(MangoError error) {
       Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
     }
   });
```

### Or the fluent API using the Builder pattern.

```java 
MangoPayBuilder builder = new MangoPayBuilder(this); // android context
builder.baseURL(baseURL)   // card pre-registration baseUrl
       .clientId(clientId) // card pre-registration clientId
       .accessKey(accessKey)   // card pre-registration accessKey
       .cardRegistrationURL(cardRegistrationURL)   // card pre-registration url
       .preregistrationData(preregistrationData)   // card pre-registration data
       .cardPreregistrationId(cardPreregistrationId)   // card pre-registration id
       .cardNumber("3569990000000157") // credit card number accepted inputs: '123412341234' or '1234-1234-1234-1234' or '1234 1234 1234 1234'
       .cardExpirationDate("0920") // credit card expiration date e.g '0920' or '11/20' or '02-19'
       .cardCvx("123") // credit card expiration cvx
       .callback(new Callback() {  // callback that returns the sdk success or failure objects
         @Override public void success(CardRegistration cardRegistration) {
           Log.d(MainActivity.class.getSimpleName(), cardRegistration.toString());
         }
         @Override public void failure(MangoError error) {
           Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
         }
       }).start();
                    
```

### Or other builder methods:

```java
// you can pass the card expiration date as a java.util.Date object
.cardExpirationDate(new Date())

// you can pass the card expiration month together with the card expiration year as integers
.cardExpirationMonth(9) 
.cardExpirationYear(2019)

// you can set the log level of the SDK
.logLevel(LogLevel.NONE|LogLevel.FULL)

```

### Code obfuscation

Don't forget to add `-keep class com.mangopay.android.sdk.* { *; }` to your proguard-rules.pro
