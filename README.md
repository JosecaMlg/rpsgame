# ROCK PAPER SCISSORS GAME

This is a simple application wich plays automatic rounds of the popular game rock paper scissors.

It's a really simple approach. 

There are 2 kinds of player.
Player 1 always chose Rock in the game, and player 2 always chose a random shape (Rock, Paper, Scissors).

This Application is an web application runing over Spring boot 2.4.3 and JavaSE-11. Is also documented in Swagger (OpenApi).

Here I'll show 2 ways to execute the APP:

- In the root of the project run in a terminal the following ...
```sh
mvn spring-boot:run
```

- Inside the Spring tool Suite IDE

1. Import the project as Maven Project 
2. Execute maven clean install.
3. Create a new configuration of SpringBootApp like this one
![image](https://user-images.githubusercontent.com/6513786/149805484-649e840f-e9c9-468b-af07-d48240e45986.png)

4. To execute, click on run

# OpenApi and Test

The app has a basic documentation by Swagger. From the documentation window you will be able to try the differnts microservices.

The URL is: http://localhost:8080/gamerps/swagger-ui/index.html

![image](https://user-images.githubusercontent.com/6513786/149806198-05295f97-ef44-42b2-b4bd-8a0e08b2175f.png)

## Test

The information about the user (Matches, rounds played, stats) are in the matchController with these services:
- /gamerps/api/user/round - Plays an automatic round with the current user (browser session)
- /gamerps/api/user/round/data - Returns all the rounds played with the result by the user so far
- /gamerps/api/user/reset -  Reset the number of the user rounds to 0.
- /gamerps/api/user/round/number - Returns the number of rounds played by the user so far.

There is another controller (GlobalDataController) who is a controller which provides information global information, it's not depends only in the current user.
- /gamerps/api/global/stats - Returns the number of games played so far by all the users. And the number of wins/draw each one.

