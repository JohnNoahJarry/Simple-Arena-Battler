package screens;

import objects.GameManager;
import objects.Unit;

import java.util.Random;
import java.util.Scanner;

public class Congratulations {
    Scanner scanner;
    Random random;
    GameManager gameManager;

    public Congratulations(Scanner scanner, Random random, GameManager gameManager) {
        this.scanner = scanner;
        this.random = random;
        this.gameManager = gameManager;
    }

    public void runCongratulations() {
        gameManager.levelUpPlayerParty();
        gameManager.incrementCurrentBattleNumber();

        gameManager.clearScreen();
        if (gameManager.getCurrentBattleNumber() != 12) {
            displayCongratulations();
            System.out.print("<< ");
            scanner.nextLine();
        }
        else {
            displayGameCompleteMessage();
            System.out.print("<< ");
            scanner.nextLine();

            gameManager.reset();
            gameManager.setIsGameOver(true);
        }

    }

    public void displayCongratulations() {
        System.out.println("""
                [===================]
                 | Congratulations |
                [===================]
                
                >> Congratulations on your win.
                
                >> As promised, here are your rewards.
                """);

        switch (gameManager.getCurrentDifficulty()) {
            case "1" -> {
                for (Unit player : gameManager.getPlayerParty()) {
                    System.out.printf(">> %s leveled up twice. (%d -> %d)\n", player.getOriginalName(), player.getLevel() - 2, player.getLevel());
                }
            }
            case "2" -> {
                for (Unit player : gameManager.getPlayerParty()) {
                    System.out.printf(">> %s leveled up 4 times. (%d -> %d)\n", player.getOriginalName(), player.getLevel() - 4, player.getLevel());
                }
            }
            case "3" -> {
                for (Unit player : gameManager.getPlayerParty()) {
                    System.out.printf(">> %s leveled up 8 times. (%d -> %d)\n", player.getOriginalName(), player.getLevel() - 8, player.getLevel());
                }
            }
        }

        System.out.println();

        System.out.println(">> Press Enter to continue.\n");
    }

    public void displayGameCompleteMessage() {
        System.out.println("""
                [===================]
                 | Congratulations |
                [===================]
                
                >> Congratulations!
                
                >> You have bested the champions
                   and rose to the rank of the new champions
                   of the arena!
                
                >> Legends of your victory today will be passed down
                   for generations to come!
                
                >> GAME COMPLETE! Thanks for playing!
                
                >> Press Enter to return to the main menu.
                """);

    }
}
