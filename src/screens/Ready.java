package screens;

import objects.GameManager;
import objects.Unit;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Ready {
    Scanner scanner;
    Random random;
    GameManager gameManager;

    public Ready(Scanner scanner, Random random, GameManager gameManager) {
        this.scanner = scanner;
        this.random = random;
        this.gameManager = gameManager;
    }

    public void runReady() {
        gameManager.clearScreen();
        displayReady(gameManager.getTeamName(), gameManager.getPlayerParty());
        System.out.print("<< ");
        scanner.nextLine();

        Difficulty difficulty = new Difficulty(scanner, random, gameManager);
        difficulty.runDifficulty();
    }

    private void displayReady(String teamName, ArrayList<Unit> playerParty) {
        System.out.printf("""
                [================================================]
                 | Team %-19s Is Ready for Battle |
                [================================================]
                
                >> Here is your team so far.
                
                """, teamName);

        gameManager.displayPlayerParty();

        System.out.println("\n\n>> Good luck.");
        System.out.println("\n>> Press Enter to continue.\n");
    }
}
