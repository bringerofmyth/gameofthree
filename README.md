Game Of Three
===================
I created a multiplayer game named Game of Three that can be played from any browser that supports WebSocket. 

# Description
* Before starting the game, the player can firstly create his/her character by entering a username.
* Then, he/she can create the game or join the game by listing all available games.
    * He/she can create the game and can wait for the second player.
 	* Or he/she can join a game and then start the game. 
* After starting the game, game log appears. Game flow is done via WebSocket over STOMP messaging.
* Not only 2 players, but also many 2-players can play at the same time. (One to one in different game rooms.)

# Used Technologies

* Java 1.8
* Spring MVC with Spring Boot
* Database H2 (In-Memory)
* Maven
* REST (Creating/joining/searching the game)
* WebSocket with STOMP (In-game messaging)
* SockJS client (for sending and listening the message)
* jQuery, HTML, CSS (for client view and interactions) 
* jUnit: For unit testing (Tested all kinds of moves in MoveTest class.)

The game information including game status are stored in in-memory H2 database. First contact of the player is with 
REST controller named GameController. After entering the game the players communicate with ActionController which has 
asynchronous messaging system (WebSocket).

While in the game you can view the game logs for each move. (Newest on top)

# How To Run
1. mvn clean install
2. java -jar target/takeaway-0.0.1.jar

After entering these, the game must start. You can connect to localhost:8080 from the browser. For proper testing, you can
open two different browser to simulate player 1 and player 2. By selecting Auto Play for both browsers, you can just watch
how these two players can play it automatically. (Without user input.)

# Simple Scenario
1. Player 1 enters a username. 
2. Player 1 creates the game by entering a game name. 
3. Player 1 waits for Player 2.
4. Player 2 enters a username.
5. Player 2 views the available games by listing the games.
6. Player 2 selects a game.
7. Player 2 joins the game and starts.
8. Both players are informed that the game has been started.
9. Player 1 starts to move. Then player 2 move until one player wins.

Note: During the game both players can select Auto Play to continue game automatically. (Without user input)
