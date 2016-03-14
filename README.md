# Forecaster-App
Android App for weather forecast search. Used Android Studio, Android App development and Facebook SDK for Android and add map features using the AERIS weather SDK.
This android app gives the weather forecast on an hourly, daily and weekly basis based on the location entered using the forecast.io API.
Data entered by the user is parsed and sent to a remote server on AWS. The server connects to Google Maps API to get the latitude and longitude values. The values are then used to query Forecast.io API which returns weather details in JSON format. The data received by server is formatted according to the specification, converted into the appropriate JSON format file and sent back to the app. The app displays the data according to the view selected by ther user.
