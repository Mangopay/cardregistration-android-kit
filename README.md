# mangopay-android #

mangopay-android makes it easy to create a card registration object based on your credit card info.

##Instalation##
### Android Studio (or Gradle) ###

No need to clone the repository or download any files -- just add this line to your app's *build.gradle* inside the dependencies section:


```
#!groovy

compile 'TODO'
```


### Eclipse ###

1. Clone the repository.
2. Be sure you've installed the Android SDK with API Level 10+. This is the only requirement for development. Our bindings require the API Level 10 as a minimum at runtime which would work on any modern version of Android.
3. Import the *mangopay* folder into Eclipse (use "Existing Projects into Workspace", not "Existing Android Code").
4. In your project settings, add the *mangopay* project under the "Libraries" section of the "Android" category.

## Usage ##


```
#!java
 
MangoPay.with(MainActivity.this) // android context
                    .baseURL(baseURL) // card pre-registration baseUrl
                    .clientId(clientId) // card pre-registration clientId
                    .accessKey(accessKey) // card pre-registration accessKey
                    .cardRegistrationURL(cardRegistrationURL) // card pre-registration url
                    .preregistrationData(preregistrationData) // card pre-registration data
                    .cardPreregistrationId(cardPreregistrationId) // card pre-registration id
                    .cardNumber("3569990000000157") // credit card number accepted inputs: '123412341234' or '1234-1234-1234-1234' or '1234 1234 1234 1234'
                    .cardExpirationDate("0920") // credit card expiration date e.g '0920' or '11/20' or '02-19'
                    .cardCvx("123") // credit card expiration cvx
                    .callback(new Callback() { // callback that returns the sdk success or failure objects
                      @Override public void success(CardRegistration cardRegistration) {
                        Log.d(MainActivity.class.getSimpleName(), cardRegistration.toString());
                      }

                      @Override public void failure(MangoError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                      }
                    }).start();
```

* other methods:

```
#!java
                  .cardExpirationDate(new Date()) // you can pass the card expiration date as a java.util.Date object

                  // you can pass the card expiration month together with the card expiration year as integers
                  .cardExpirationMonth(9) 
                  .cardExpirationYear(2019)

                  .logLevel(LogLevel.NONE|LogLevel.FULL) // you can set the log level of the SDK

```
