# Simple Arena Battler

A simple terminal game where you fight 10 battles to then overthrow the Arena's champions.

## Version 1.1.2 Changelog

+ Modified the SPD stat to better apply evasion and critical strike chances.
+ Fixed 100% chances not displaying properly under enemy team stats.
+ Fixed certain messages not appearing in the message log after each round in a battle.

## Summary

This Java terminal game has you creating a team of recruits to battle and overthrow the Arena's champion team.

Once your team of 2 is ready, you will begin battling 10 teams of 2. Defeat the opposing team to advance to the next battle.
Your game is over when you lose both team members in one battle. If you only lose 1 team member, a new recruit will be added to your team.

There are 4 main stats in the game that increase as a unit levels up. 
HP is how much damage a unit can take before the unit becomes defeated. 
ATK is how much damage a unit does per attack. 
DEF is how much damage a unit randomly blocks as they get hit with an attack. The minimum block amount is 0.
SPD determines who acts first in a battle. SPD also affects your chance to inflict a critical hit as well as affecting your evasion.

You may notice that when in a battle, the enemies have additional stats such as SUN, STR, and MON.
This is their chances to choose an element for their magic attack.

There is a magic triangle in this game.

Sun is effective against Star, Star is effective against Moon, and Moon is effective against Sun.

If you attack a unit with an effective element, your damage is doubled. It stacks with critical strikes making a 4x attack possible.
If you attack a unit with an ineffective element, your damage is halved.

You can also defend in this game if you don't want to attack. Defending is useful when the enemies' chances of using Sun, Star, or Moon magic is too unpredictable.

## Requirements

This terminal game was made in Java 21.0.11. To play the game, install Java 21 or higher and download the .jar file in the releases tab and navigate to the .jar file using a terminal program.
Run the .jar file using `java -jar SimpleArenaBattler.jar`.

## Credits

This project uses the proper name database of ENAMDICT/JMnedict (https://www.edrdg.org/enamdict/enamdict_doc.html) to generate names for the units in-game.
