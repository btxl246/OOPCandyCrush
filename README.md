# OOPCandyCrush
![GitHub top language](https://img.shields.io/github/languages/top/btxl246/OOPCandyCrush) ![GitHub repo size](https://img.shields.io/github/repo-size/btxl246/OOPCandyCrush) ![GitHub contributors](https://img.shields.io/github/contributors/btxl246/OOPCandyCrush) 

An Object-Oriented Programming project based on the game Candy Crush Saga by King.

## Table of Contents
* [Introduction](#introduction)

* [Requirements](#requirements)

* [Installations](#installations)

* [Run This Game](#run-this-game)

  * [Using CLI](#_using-cli_)
  
  * [Using IDEA](#_using-ide_)

* [How to Play](#how-to-play)

* [Screenshots](#screenshots)

## Introduction
Candy Crush is a free-to-play match-three puzzle video game released by King on April 12/2012 for Facebook. Other versions for iOS, Android, Windows Phone, and Windows 10 followed after. Right now, the game is still alive with 9.2 million players and has become most successful on mobile devices.

Our project builds on the match-three principle on a simpler level with our own designs. 

## Requirements
Operating system: Windows.

* Java Development Kit 15 [(link)](https://www.oracle.com/java/technologies/javase-downloads.html).

* Intellij IDEA by JetBrains [(link)](https://www.jetbrains.com/idea/download/).

IMPORTANT: Remember to set PATH environment variables.

## Installations
* Install the Java Development Kit.

* Clone this repository.
  ```
  git clone https://github.com/btxl246/OOPCandyCrush.git
  ```
  
* Or download the most recent code version as a .zip file and extract it.

* Install the font from path "\OOPCandyCrush\in\CANDY.TFF".

## Run This Game
#### _Using CLI_
1. Open a command line program and navigate to the repository.
   ```
   cd <path>
   ```
   
2. Run the executable.
   ```
   java -jar OOPCandyCrush.jar
   ```

#### _Using IDE_
1. Open the repository in an IDE (preferably Intellij IDEA).

2. Run Game.java

## How to Play
The objective is to match tiles of the same variations and make them disappear to earn points, then the tiles on top of them will drop down and new tiles will appear.

The player makes a move by clicking two adjacent tiles to switch them. If a match occurs, the grid clears the matched tiles and randomizes new tiles which are dropped down.

* If there are more than one match after a move, the game updates the GUI after all those matches are processed.

* The total matches are tallied and displayed at the top.

* Each tile is given a score, and the total score is calculated accordingly and displayed at the top.

The player is able to play until there is no more possible move. Then, the game is over.

## Screenshots
![HOME SCREEN](https://github.com/btxl246/OOPCandyCrush/blob/main/in/images/screenshots/home.png)

![PLAY MODE](https://github.com/btxl246/OOPCandyCrush/blob/main/in/images/screenshots/playMode.png)

![CUSTOMIZE SCREEN](https://github.com/btxl246/OOPCandyCrush/blob/main/in/images/screenshots/customize.png)

![PLAY SCREEN](https://github.com/btxl246/OOPCandyCrush/blob/main/in/images/screenshots/playCustomize.png)

![GAME OVER](https://github.com/btxl246/OOPCandyCrush/blob/main/in/images/screenshots/gameOver.png)
