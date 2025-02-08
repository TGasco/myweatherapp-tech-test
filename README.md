# MyWeather App Tech Test

Welcome to the MyWeather App Tech Test.

## The Challenge

You are tasked with implementing two new features in the app:

1. **Daylight Hours Comparison:** Given two city names, compare the length of the daylight hours between them and return the city with the longest day. In this context, "daylight hours" means the time between sunrise and sunset.

2. **Rain Check:** Given two city names, check which city it is currently raining in.

In addition to implementing these 2 features, you will also need to write tests verifying that your code works as expected.

If possible, include exception handling in the controller.

Finally, you can write any documentation as you see fit, explaining your choices and how the code works.

## The Codebase

The codebase is a Java application built with the Spring framework. It includes a `WeatherController` class where you will add your new features.

## Implementation Details

You will need to implement these features by adding new endpoints to the `WeatherController`.

### Prerequisites

- [Java sdk 17](https://openjdk.java.net/projects/jdk/17/)
- [Maven 3.6.3+](https://maven.apache.org/install.html)
- API key for [Visual Crossing Weather API](https://www.visualcrossing.com/weather-data-editions). 
  - This can be done by creating a free account on the above link. Then you will need to add your key to the `weather.visualcrossing.key` field in src/main/resources/application.properties

## Submission

* Push the downloaded version of this repo to your Github
* Make a branch for your changes
* Once you're ready to submit, raise a Pull Request to merge your changes with your main branch and share the repo with us.

Good luck!
