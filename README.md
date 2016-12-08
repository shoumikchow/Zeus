# Zeus


This was made as part of my CSE491 (Mobile Programming) course in Brac University in Spring 2016 but I've made a lot of changes since then.

This is a crowdsourced weather app, named Zeus, which takes normal weather forecast from the OpenWeather Map API. This is a regular weather forecast, which gives weather forecast of the next 5 days on 3 hour intervals. The forecast is shown in a ListView. The current weather condition is shown on top of the ListView along with the conditions.

As for what makes this different from all the other weather apps in the market: it uses crowdsourcing. This is important because as we all know, the weather reports that we get from sources online are hardly accurate. The weather data that is used by apps such as AccuWeather, etc. take the data from government weather stations, which is unreliable. Crowdsourcing increases efficiency and reliability.

For crowdsourcing, users put markers on the Map, upon which they are transferred to another activity where they say firsthand what the weather is like in the position where they have put the marker. This data is stored in a database. The marker is put in the sublocality of the place where the user clicks. This is to protect the privacy by not giving out the exact location. This also helps to get the percentage of what the entries at a particular sublocality are.

When another user opens up the map in the location of the dropped pin, the user sees the condition of the place as the title of the marker. For example, if 3 users enter the weather to be “Sunny” in Brooklyn and 2 other people says it is “Rainy”, the marker will be shown at the center of Gulshan with the title “60% sunny and 40% rainy at Brooklyn”.


The data is stored in Parse. The data that is in Parse is deleted after 6 hours of marker creation so as to avoid redundant data. To do this, a JavaScript code is run on the Parse backend (Code is attached.  The code is not active as of now).

 
Tools used:

1. Parse SDK
2. back{4}app BaaS
2. Retrofit Library
3. Picasso Library
4. Google Maps API
5. Google Location API.
6. Open Weather Maps API
