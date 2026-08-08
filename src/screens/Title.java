package screens;

import objects.GameManager;

import java.util.Random;
import java.util.Scanner;

public class Title {
    Scanner scanner;
    Random random;
    GameManager gameManager;

    public Title(Scanner scanner, Random random, GameManager gameManager) {
        this.scanner = scanner;
        this.random = random;
        this.gameManager = gameManager;
    }

    public void runTitle() {
        String choice;

        while (true) {
            gameManager.setIsGameOver(false);

            gameManager.clearScreen();
            displayTitle();
            System.out.print("<< ");
            choice = scanner.nextLine();

            if (choice.equals("1")) {
                TeamName teamNameSelector = new TeamName(scanner, random, gameManager);
                teamNameSelector.runTeamCreator();
            }
            else if (choice.equals("2")) {
                break;
            }
        }
    }

    private void displayTitle() {
        System.out.println("""
                [===============================]
                 | Simple Arena Battler v1.2.0 |
                [===============================]
                
                [1] = Start
                [2] = Exit
                """);
    }
}
