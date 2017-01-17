# APCS-Term-1-Final-Project
By Md Abedin and Othman Bichouna

An ASCII-art-based dungeon crawling game inspired by Rogue. In the game, your character walks around and
goes through different randomly generated floors. Each floor is constructed of randomly generated rooms that
contain enemies that follow and attack your player. Your player can find random items to defend themself with.
Go through enough of the floors to find the amulet. When you find the amulet take it back to the top to win!

Instructions:
1. Get into the "Game Files" folder
2. Compile with "javac -cp libjcsi.jar;. Rogue.java", or "javac -cp libjcsi.jar:. Rogue.java" if you're on Linux
3. Run with "java -cp libjcsi.jar;. Rogue", or "javac -cp libjcsi.jar:. Rogue.java" if you're on Linux

How to Play:
Your goal is to get to the last floor, pick up an amulet, and travel back to the top floor and escape the dungeon. Walk over items to pick them up.
% = Staircase
? = Amulet

-Move with arrow keys
-Go down a floor with ">", go up a floor with "<" while on a staircase
-Attack enemies by moving towards them

Known Bugs:
-Items will sometimes not be picked up
-Items will sometimes spawn on top of each other
-Enemy AI will sometimes cause an error
-VERY RARE: Paths will not generate properly between rooms
