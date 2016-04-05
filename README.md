# mangopay-android #

mangopay-android makes it easy to create a card registration object based on your credit card info.

##Installation##
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

* *mangopay sdk* creation:

```
# !java

             // holds the card registration data
             MangoSettings mSettings = new MangoSettings(baseURL, clientId, cardPreregistrationId,cardRegistrationURL, preregistrationData, accessKey);
                          
             // using the default constructor where you should pass the android context and  the settings object
             MangoPay mangopay = new MangoPay(this, mSettings);
             
             // or using the mangopay builder
             MangoPay mangopay = new MangoPayBuilder(this).build();

```

* sdk usage:

```
# !java
             
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

* Fluent API using the Builder pattern.

```
#!java
 
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

* other builder methods:

```
# !java

                  // you can pass the card expiration date as a java.util.Date object
                  .cardExpirationDate(new Date())

                  // you can pass the card expiration month together with the card expiration year as integers
                  .cardExpirationMonth(9) 
                  .cardExpirationYear(2019)

                  // you can set the log level of the SDK
                  .logLevel(LogLevel.NONE|LogLevel.FULL)

```
