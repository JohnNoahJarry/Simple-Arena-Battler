package screens;

import objects.GameManager;

import java.util.Random;
import java.util.Scanner;

public class GameOver {
    Scanner scanner;
    Random random;
    GameManager gameManager;

    public GameOver(Scanner scanner, Random random, GameManager gameManager) {
        this.scanner = scanner;
        this.random = random;
        this.gameManager = gameManager;
    }

    public void runGameOver() {
        gameManager.clearScreen();
        displayGameOver();
        System.out.print("<< ");
        scanner.nextLine();
    }

    private void displayGameOver() {
        System.out.println("""
                [=============]
                 | Game Over |
                [=============]
                
                >> Your team got wiped out.
                
                >> Your reputation as a team leader has fallen.
                
                >> GAME OVER.
                
                >> Press Enter to continue.
                """);
    }
}
