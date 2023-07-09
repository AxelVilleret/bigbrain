# BigBrain

## Description

The goal was to create his own bot on Telegram messaging and to be able to chat with it. To do this, I first exposed my own API using Spring Boot. The latter is used to simplify exchanges with famous APIs such as OpenWeather, Telegram or OpenAI by reducing the data exchanged to those useful for the project. Secondly, to create the bot, I set up a pooling system so that my program was kept informed of the latest messages received by the bot. It will then, depending on the message received, query the appropriate endpoint of my API and return a message containing the response to the client via the Telegram API.

This project was an opportunity for me to perfect my mastery of REST APIs and to realize the extent of the possibilities then offered. I was also able to apply the good practices recently learned in Design Pattern.

![Big Brain](https://axel-villeret.netlify.app/bigbrain.95899cfb.png)
