*This project was for a Scala course organised by Aalto University in 2014. The project specifications were to write any complete game or program from a scratch.*

# Game of Thrones -game

## Idea and Goal
You are a girl named Arya whose family members have been captured by the evil enemies. Your goal is to fight against the enemies and release your relatives.

## Captured relatives
* Sansa Stark (sister)
* Robb Stark (brother)

## Victory
You win the game when you return home with your free relatives.

## Defeat
You lose the game if you die in a battle.

## Home
Your home is in Winterfell where you can refill your Healt Points by using a command "refill".

## Battle
You can step into a fight by using a command "fight". In order to fight, you have to carry at least one weapon. You are able to step out of the fight by using a command "exit". This makes it possible to go home and refill your Health Points in case the enemy is too strong. In addition, you are able to search for more powerful weapons if needed.

Your enemy's current Health Points will be saved for the next fight.

## Weapons
In the world of Game of Thrones, there are three possible choices of weapons:

1. Needle (does 20 hp damage)
2. OathKeeper (does 50 hp damage)
3. Dragonglass (does 90 hp damage)

## List of all commands
* "items" -> Prints a description of the items laying on the ground in the current area
* "area" -> Prints a description of the current area
* "exits" -> Prints a description of the choices where you are able to go from the current area
* "enemies" -> Prints a description of the possible enemy in the current area
* "examine" -> Prints a description of the item in your possession
* "drop" -> Drop the item in your possession on the ground (leaves the item in the current area)
* "ask [name of the person]" -> Ask something from a person in the current area
* "inventory" -> Prints a description of the items in your possession and the relatives you have saved already
* "tips" -> Prints game tips
* "hp" -> Prints your current Health Points
* "help" -> Prints all possible commands
* "home" -> Prints the name of your home
* "quit" -> Quits the game
