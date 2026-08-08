package screens;

import objects.GameManager;

import java.util.Random;
import java.util.Scanner;

public class Difficulty {
    Scanner scanner;
    Random random;
    GameManager gameManager;

    public Difficulty(Scanner scanner, Random random, GameManager gameManager) {
        this.scanner = scanner;
        this.random = random;
        this.gameManager = gameManager;
    }

    public void runDifficulty() {
        while (true) {
            if (gameManager.getCurrentBattleNumber() != 11) {
                gameManager.clearScreen();
                displayDifficulty();
                System.out.print("<< ");
                String choice = scanner.nextLine();

                String yesOrNo = "";
                if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                    gameManager.setCurrentDifficulty(choice);
                    while (true) {
                        gameManager.clearScreen();
                        displayDifficultyConfirmation();
                        System.out.print("<< ");
                        yesOrNo = scanner.nextLine();

                        if (yesOrNo.equals("1")) {
                            Battle battle = new Battle(scanner, random, gameManager);
                            battle.runBattle();
                            break;
                        } else if (yesOrNo.equals("2")) {
                            break;
                        }
                    }
                }

                if (yesOrNo.equals("1")) {
                    break;
                }
            }
            else {
                Battle battle = new Battle(scanner, random, gameManager);
                battle.runBattle();
                break;
            }
        }
    }

    private void displayDifficulty() {
        System.out.printf("""
                [===========================]
                 | Battle Number: %-2d of 10 |
                [===========================]
                
                [==============]
                 | Difficulty |
                [==============]
                
                >> Choose the difficulty of your next battle.
                
                [1] = Easy (1x difficulty and 2x player scaling)
                [2] = Medium (2x difficulty and 4x player scaling)
                [3] = Hard (4x difficulty and 8x player scaling)
                
                """, gameManager.getCurrentBattleNumber());
    }

    private void displayDifficultyConfirmation() {
        String difficulty = gameManager.getCurrentDifficulty();

        switch (difficulty) {
            case "1" -> difficulty = "easy";
            case "2" -> difficulty = "medium";
            case "3" -> difficulty = "hard";
        }

        System.out.printf("""
                [===========================]
                 | Battle Number: %-2d of 10 |
                [===========================]
                
                [==============]
                 | Difficulty |
                [==============]
                
                >> You have chosen to fight a(n) %s battle.
                
                >> Is this okay?
                
                [1] = Yes
                [2] = No
                
                """, gameManager.getCurrentBattleNumber(), difficulty);
    }
}
