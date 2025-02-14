# MyWeather App Tech Test

MyWeather App Tech Test is a simple weather application built using Spring Boot that allows users to search for the weather forecast for a specific location, compare daylight hours, and perform a rain check. The application uses the [Visual Crossing Weather API](https://www.visualcrossing.com/weather-data-editions) to retrieve the weather forecast data.

## Features

- Search for the weather forecast for a specific location
- Compare daylight hours between two or more locations
- Perform a rain check on 1 or more locations, to see if it is currently raining.

### Prerequisites

- [Java sdk 17](https://openjdk.java.net/projects/jdk/17/)
- [Maven 3.6.3+](https://maven.apache.org/install.html)
- API key for [Visual Crossing Weather API](https://www.visualcrossing.com/weather-data-editions). 
  - This can be done by creating a free account on the above link. Then you will need to add your key to the `weather.visualcrossing.key` field in src/main/resources/application.properties

### Configuration
The application uses the `application.properties` file to store configuration properties. The following properties are available:
- weather.visualcrossing.url - The base URL for the Visual Crossing Weather API (this should not be changed unless the API URL changes)
- weather.visualcrossing.key - The API key for the Visual Crossing Weather API (this should be set to your API key)
                           - By default this looks for the key in the environment variable `WEATHER_VISUALCROSSING_KEY`
- weather.visualcrossing.unitGroup - The unit group to use for the weather forecast data (Options: us, metric, uk [default: metric])
- weather.visualcrossing.contentType - The content type to use for the weather forecast data (Options: json, csv [default: json])

## Installation
1. Clone the repository
```
git clone 
```

2. Build the project
```
mvn clean install
```

3. Run the application
```
mvn spring-boot:run
```

4. Access the application
The application should now be running on `http://localhost:8080/`

## Usage
The applications features are accessible using the following endpoints:
- `/forecast/{location}` - Get the weather forecast for a specific location
- `/longestday?city={city1}&city={city2}...&city={cityN}` - Compare daylight hours between two or more locations
- `/raincheck?city={city1}&city={city2}...&city={cityN}` - Perform a rain check on 1 or more locations, to see if it is currently raining.

## How it Works
The application uses the [Visual Crossing Weather API](https://www.visualcrossing.com/weather-data-editions) to retrieve the weather forecast data. The application makes a request to the API using the `getByCity()` method in the `VisualcrossingRepository` class. The response is then parsed to a `CityInfo` object which is then used to display the weather forecast data in JSON format.

The `WeatherController` class is responsible for handling the requests and responses for the application. The controller uses the `VisualcrossingRepository` class to get the weather forecast data for a specific location. The controller then uses the `CityInfo` object to perform the required operations.

The `CityInfo` class is a simple POJO class that is used to store the weather forecast data for a specific location. The class has public Getters for each field in the response. Setters are not required as the data is set using the constructor and should be immutable.

The `WeatherService` class is responsible for handling the business logic for the application. The service class uses the `VisualcrossingRepository` class to get the weather forecast data for a specific location.

Exception handling is done globally using the `GlobalExceptionHandler` class. The class uses the `@ControllerAdvice` annotation to handle exceptions globally for the application, and the `@ExceptionHandler` annotation to handle specific exceptions.

1. Getting the weather forecast for a specific location
The application uses the `getByCity()` method to get the forecast data for the specified location. The response is then parsed to a `CityInfo` object which is then used to display the weather forecast data in JSON format.
#### Sample Request
```
http://localhost:8080/forecast/London
```

#### Sample Response
```
{
    "address": "London",
    "description": "Warming up with no rain expected.",
    "currentConditions": {
        "temp": "2.7",
        "sunrise": "07:15:59",
        "sunset": "17:14:15",
        "feelslike": "2.7",
        "humidity": "73.7",
        "conditions": "Overcast"
    },
    "days": [
        {
            "datetime": "2025-02-14",
            "temp": "2.3",
            "tempmax": "4.1",
            "tempmin": "0.8",
            "conditions": "Overcast",
            "description": "Cloudy skies throughout the day."
        },
        {
            "datetime": "2025-02-15",
            "temp": "3.0",
            "tempmax": "4.4",
            "tempmin": "1.8",
            "conditions": "Overcast",
            "description": "Cloudy skies throughout the day."
        },
        {
            "datetime": "2025-02-16",
            "temp": "2.6",
            "tempmax": "4.6",
            "tempmin": "1.2",
            "conditions": "Partially cloudy",
            "description": "Partly cloudy throughout the day."
        },
        {
            "datetime": "2025-02-17",
            "temp": "3.4",
            "tempmax": "6.4",
            "tempmin": "0.5",
            "conditions": "Partially cloudy",
            "description": "Partly cloudy throughout the day."
        },
        {
            "datetime": "2025-02-18",
            "temp": "6.7",
            "tempmax": "10.6",
            "tempmin": "4.3",
            "conditions": "Overcast",
            "description": "Cloudy skies throughout the day."
        },
        {
            "datetime": "2025-02-19",
            "temp": "8.5",
            "tempmax": "10.9",
            "tempmin": "5.5",
            "conditions": "Overcast",
            "description": "Cloudy skies throughout the day."
        },
        {
            "datetime": "2025-02-20",
            "temp": "9.9",
            "tempmax": "11.8",
            "tempmin": "7.0",
            "conditions": "Overcast",
            "description": "Cloudy skies throughout the day."
        },
        {
            "datetime": "2025-02-21",
            "temp": "11.3",
            "tempmax": "12.3",
            "tempmin": "8.7",
            "conditions": "Overcast",
            "description": "Cloudy skies throughout the day."
        },
        {
            "datetime": "2025-02-22",
            "temp": "8.4",
            "tempmax": "11.8",
            "tempmin": "5.9",
            "conditions": "Partially cloudy",
            "description": "Partly cloudy throughout the day."
        },
        {
            "datetime": "2025-02-23",
            "temp": "8.8",
            "tempmax": "10.7",
            "tempmin": "6.3",
            "conditions": "Partially cloudy",
            "description": "Partly cloudy throughout the day."
        },
        {
            "datetime": "2025-02-24",
            "temp": "11.0",
            "tempmax": "12.4",
            "tempmin": "8.6",
            "conditions": "Overcast",
            "description": "Cloudy skies throughout the day."
        },
        {
            "datetime": "2025-02-25",
            "temp": "8.5",
            "tempmax": "11.4",
            "tempmin": "6.1",
            "conditions": "Partially cloudy",
            "description": "Partly cloudy throughout the day."
        },
        {
            "datetime": "2025-02-26",
            "temp": "6.7",
            "tempmax": "9.1",
            "tempmin": "4.8",
            "conditions": "Partially cloudy",
            "description": "Clearing in the afternoon."
        },
        {
            "datetime": "2025-02-27",
            "temp": "9.1",
            "tempmax": "9.9",
            "tempmin": "7.3",
            "conditions": "Overcast",
            "description": "Cloudy skies throughout the day."
        },
        {
            "datetime": "2025-02-28",
            "temp": "7.4",
            "tempmax": "10.0",
            "tempmin": "5.9",
            "conditions": "Partially cloudy",
            "description": "Partly cloudy throughout the day."
        }
    ]
}
```

2. Finding the longest day between two or more locations
The application uses the `getByCity()` method to get the forecast data for each location. Sunrise and sunset times are then extracted from the `CityInfo` object using public Getters. The application then calculates the daylight hours (sunset - sunrise) for each location and returns the location with the longest daylight hours in JSON format, as well as the daylight hours for each location.
#### Sample Request
```
http://localhost:8080/longestday?city=London&city=Manchester
```

#### Sample Response
```
{
    "cities": [
        {
            "city": "london",
            "daylightTime": "09:58:16"
        },
        {
            "city": "manchester",
            "daylightTime": "09:48:44"
        }
    ],
    "longestDay": "london"
}
```

3. Rain Check
The application uses the `getByCity()` method to get the forecast data for each location. The `CityInfo` object is then used to check if it is currently raining in each location. The application then returns a list of locations where it is currently raining in JSON format, as well as the weather conditions for each location.
#### Sample Request
```
http://localhost:8080/raincheck?city=London&city=Manchester
```

#### Sample Response
```
{
    "rainCheck": [
        {
            "city": "london",
            "isRaining": false,
            "conditions": "Overcast"
        },
        {
            "city": "manchester",
            "isRaining": true,
            "conditions": "Rain"
        }
    ],
    "isRaining": [
        "manchester"
    ]
}
```

## Testing
The application has been tested using JUnit and Mockito. The tests can be found in the `src/test/java/com/weatherapp/myweatherapp` directory.

To run the tests, use the following command:
```
mvn test
```