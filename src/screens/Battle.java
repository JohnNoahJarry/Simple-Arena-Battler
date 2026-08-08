package screens;

import objects.GameManager;
import objects.Unit;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Battle {
    Scanner scanner;
    Random random;
    GameManager gameManager;

    public Battle(Scanner scanner, Random random, GameManager gameManager) {
        this.scanner = scanner;
        this.random = random;
        this.gameManager = gameManager;
    }

    public void runBattle() {
        if (gameManager.getCurrentBattleNumber() != 11) {
            gameManager.createEnemyParty();
        }
        else {
            gameManager.createBoss();
        }

        gameManager.clearScreen();
        displayBattleIntro();
        System.out.print("<< ");
        scanner.nextLine();

        boolean isBattleOver = false;
        boolean isAllPlayersDefeated = true;
        boolean isAllEnemiesDefeated = true;

        while (!isBattleOver) {
            isAllPlayersDefeated = true;
            isAllEnemiesDefeated = true;

            for (Unit player : this.gameManager.getPlayerParty()) {
                if (!player.getStatus().equals("Defeated")) {
                    isAllPlayersDefeated = false;
                    break;
                }
            }

            for (Unit enemy : this.gameManager.getEnemyParty()) {
                if (!enemy.getStatus().equals("Defeated")) {
                    isAllEnemiesDefeated = false;
                    break;
                }
            }

            if (isAllPlayersDefeated || isAllEnemiesDefeated) {
                isBattleOver = true;
                continue;
            }

            gameManager.rollEnemyPartyMagicChances();
            while (true) {
                String yesOrNo;
                for (int i = 0; i < 2; i++) {
                    if (!gameManager.getPlayerParty().get(i).getStatus().equals("Defeated")) {
                        while (true) {
                            gameManager.clearScreen();
                            displayBattleTargetOptions(i);
                            System.out.print("<< ");
                            String actionChoice = scanner.nextLine();

                            if (actionChoice.equals("1") && !gameManager.getEnemyParty().getFirst().getStatus().equals("Defeated")) {
                                gameManager.getPlayerParty().get(i).setTarget(gameManager.getEnemyParty().getFirst());
                                gameManager.getPlayerParty().get(i).setStatus("Attacking");
                                break;
                            } else if (actionChoice.equals("2") && !gameManager.getEnemyParty().get(1).getStatus().equals("Defeated")) {
                                gameManager.getPlayerParty().get(i).setTarget(gameManager.getEnemyParty().get(1));
                                gameManager.getPlayerParty().get(i).setStatus("Attacking");
                                break;
                            } else if (actionChoice.equals("3")) {
                                gameManager.getPlayerParty().get(i).setStatus("Defending");
                                break;
                            }
                        }

                        if (!gameManager.getPlayerParty().get(i).getStatus().equals("Defending")) {
                            label:
                            while (true) {
                                gameManager.clearScreen();
                                displayBattleMagicOptions(i);
                                System.out.print("<< ");
                                String magicChoice = scanner.nextLine();

                                switch (magicChoice) {
                                    case "1":
                                        gameManager.getPlayerParty().get(i).setSelectedMagic("Sun");
                                        break label;
                                    case "2":
                                        gameManager.getPlayerParty().get(i).setSelectedMagic("Star");
                                        break label;
                                    case "3":
                                        gameManager.getPlayerParty().get(i).setSelectedMagic("Moon");
                                        break label;
                                }
                            }
                        }
                    }
                }

                while (true) {
                    gameManager.clearScreen();
                    displayBattleActionsConfirmation();
                    System.out.print("<< ");
                    yesOrNo = scanner.nextLine();

                    if (yesOrNo.equals("1")) {
                        break;
                    } else if (yesOrNo.equals("2")) {
                        break;
                    }
                }

                if (yesOrNo.equals("1")) {
                    gameManager.prepareEnemyParty();
                    ArrayList<String> messages = gameManager.executeActions();

                    gameManager.clearScreen();
                    displayBattleMessageLog(messages);
                    System.out.print("<< ");
                    scanner.nextLine();

                    break;
                }
            }
        }

        if (isAllPlayersDefeated) {
            gameManager.setIsGameOver(true);
            gameManager.reset();

            GameOver gameOver = new GameOver(scanner, random, gameManager);
            gameOver.runGameOver();
        }

        if (isAllEnemiesDefeated) {
            gameManager.cleanPlayerParty();

            Congratulations congratulations = new Congratulations(scanner, random, gameManager);
            congratulations.runCongratulations();
        }
    }

    private void displayBattleIntro() {
        System.out.printf("""
                [===========================]
                 | Battle Number: %-2d of 10 |
                [===========================]
                
                [Team %s]
                """, gameManager.getCurrentBattleNumber(), gameManager.getEnemyTeamName());

        for (int i = 0; i < gameManager.getEnemyParty().size(); i++) {
            System.out.print("[==========================] ");
        }

        System.out.println();

        for (Unit enemy : gameManager.getEnemyParty()) {
            System.out.printf(" | %-22s |  ", enemy.getOriginalName());
        }

        System.out.println();

        for (Unit enemy : gameManager.getEnemyParty()) {
            System.out.printf(" |    HP: %-4d / %-4d     |  ", enemy.getCurrentHP(), enemy.getMaximumHP());
        }

        System.out.println();

        for (Unit enemy : gameManager.getEnemyParty()) {
            System.out.printf(" |   LVL: %-4d ATK: %-4d  |  ", enemy.getLevel(), enemy.getAtk());
        }

        System.out.println();

        for (Unit enemy : gameManager.getEnemyParty()) {
            System.out.printf(" |   DEF: %-4d SPD: %-4d  |  ", enemy.getDef(), enemy.getSpd());
        }

        System.out.println();

        for (int i = 0; i < gameManager.getEnemyParty().size(); i++) {
            System.out.print("[==========================] ");
        }

        System.out.println();
        System.out.println();

        gameManager.displayPlayerParty();

        System.out.println();

        if (gameManager.getCurrentBattleNumber() != 11) {
            System.out.println("""
                
                >> The opposing team has arrived. It's time to fight.
                
                >> Press Enter to continue.
                """);
        }
        else {
            System.out.println("""
                
                >> The champions have arrived. This is it. Good luck.
                
                >> Press Enter to continue.
                """);
        }
    }

    private void displayBattleTargetOptions(int currentPlayer) {
        displayEnemyParty();

        System.out.println();
        System.out.println();

        gameManager.displayPlayerParty();

        System.out.println();

        System.out.printf("""
                
                >> Choose an action for [%s].
                
                """, gameManager.getPlayerParty().get(currentPlayer).getOriginalName());

        for (int i = 0; i < gameManager.getEnemyParty().size(); i++) {
            if (!gameManager.getEnemyParty().get(i).getStatus().equals("Defeated")) {
                System.out.printf("[%d] = Attack %s\n", i + 1, gameManager.getEnemyParty().get(i).getOriginalName());
            }
        }

        System.out.println();

        System.out.println("[3] = Defend (Reduce incoming damage by 75%)\n");
    }

    private void displayBattleMagicOptions(int currentPlayer) {
        displayEnemyParty();

        System.out.println();
        System.out.println();

        gameManager.displayPlayerParty();

        System.out.println();

        System.out.printf("""
                
                >> Choose an element for [%s's] magic attack.
                
                [1] = Sun Magic
                [2] = Star Magic
                [3] = Moon Magic
                
                """, gameManager.getPlayerParty().get(currentPlayer).getOriginalName());
    }

    private void displayBattleActionsConfirmation() {
        displayEnemyParty();

        System.out.println();
        System.out.println();

        gameManager.displayPlayerParty();

        System.out.println("\n\n>> Here are your chosen actions.\n");

        for (Unit player : gameManager.getPlayerParty()) {
            if (player.getStatus().equals("Attacking")) {
                System.out.printf(">> %s will attack %s with %s magic.\n", player.getOriginalName(), player.getTarget().getOriginalName(), player.getSelectedMagic());
            }
            else if (player.getStatus().equals("Defending")) {
                System.out.printf(">> %s will defend.\n", player.getOriginalName());
            }
        }

        System.out.println("""
                
                >> Is this okay?
                
                [1] = Yes
                [2] = No (Reset back to the beginning)
                """);
    }

    private void displayBattleMessageLog(ArrayList<String> messages) {
        displayEnemyParty();

        System.out.println();
        System.out.println();

        gameManager.displayPlayerParty();

        System.out.println();
        System.out.println();

        if (messages.isEmpty()) {
            System.out.println(">> Everyone defended.");
        }

        for (String message : messages) {
            System.out.printf(">> %s\n", message);
        }

        System.out.println();

        System.out.println(">> Press Enter to continue.\n");
    }

    private void displayEnemyParty() {
        System.out.printf("""
                [===========================]
                 | Battle Number: %-2d of 10 |
                [===========================]
                
                [Team %s]
                """, gameManager.getCurrentBattleNumber(), gameManager.getEnemyTeamName());

        for (int i = 0; i < gameManager.getEnemyParty().size(); i++) {
            System.out.print("[==========================] ");
        }

        System.out.println();

        for (Unit enemy : gameManager.getEnemyParty()) {
            System.out.printf(" | %-22s |  ", enemy.getName());
        }

        System.out.println();

        for (Unit enemy : gameManager.getEnemyParty()) {
            System.out.printf(" |    HP: %-4d / %-4d     |  ", enemy.getCurrentHP(), enemy.getMaximumHP());
        }

        System.out.println();

        for (Unit enemy : gameManager.getEnemyParty()) {
            System.out.printf(" |   LVL: %-4d ATK: %-4d  |  ", enemy.getLevel(), enemy.getAtk());
        }

        System.out.println();

        for (Unit enemy : gameManager.getEnemyParty()) {
            System.out.printf(" |   DEF: %-4d SPD: %-4d  |  ", enemy.getDef(), enemy.getSpd());
        }

        System.out.println();

        for (Unit enemy : gameManager.getEnemyParty()) {
            System.out.printf(" |   SUN: %-3d%% STR: %-3d%%  |  ", enemy.getSunChance(), enemy.getStarChance());
        }

        System.out.println();

        for (Unit enemy : gameManager.getEnemyParty()) {
            System.out.printf(" |        MON: %-3d%%       |  ", enemy.getMoonChance());
        }

        System.out.println();

        for (int i = 0; i < gameManager.getEnemyParty().size(); i++) {
            System.out.print("[==========================] ");
        }
    }
}
