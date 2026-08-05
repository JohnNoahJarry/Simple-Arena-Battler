package screens;

import objects.GameManager;
import objects.Unit;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TeamRecruiter {
    Scanner scanner;
    Random random;
    GameManager gameManager;

    public TeamRecruiter(Scanner scanner, Random random, GameManager gameManager) {
        this.scanner = scanner;
        this.random = random;
        this.gameManager = gameManager;
    }

    public void runTeamRecruiter() {
        while (!gameManager.getIsGameOver()) {
            for (int i = gameManager.getPlayerParty().size(); i < 2; i++) {
                gameManager.createTeamRecruits();
                while (true) {
                    gameManager.clearScreen();
                    displayTeamRecruits();
                    System.out.print("<< ");
                    String choice = scanner.nextLine();

                    String yesOrNo;
                    if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                        while (true) {
                            String chosenRecruitName;

                            if (choice.equals("1")) {
                                chosenRecruitName = gameManager.getTeamRecruits().getFirst().getOriginalName();
                            } else if (choice.equals("2")) {
                                chosenRecruitName = gameManager.getTeamRecruits().get(1).getOriginalName();
                            } else {
                                chosenRecruitName = gameManager.getTeamRecruits().getLast().getOriginalName();
                            }

                            gameManager.clearScreen();
                            displayTeamRecruitConfirmation(chosenRecruitName);
                            System.out.print("<< ");
                            yesOrNo = scanner.nextLine();

                            if (yesOrNo.equals("1")) {
                                if (choice.equals("1")) {
                                    gameManager.getPlayerParty().add(gameManager.getTeamRecruits().getFirst());
                                } else if (choice.equals("2")) {
                                    gameManager.getPlayerParty().add(gameManager.getTeamRecruits().get(1));
                                } else {
                                    gameManager.getPlayerParty().add(gameManager.getTeamRecruits().getLast());
                                }

                                break;
                            } else if (yesOrNo.equals("2")) {
                                break;
                            }
                        }

                        if (yesOrNo.equals("1")) {
                            break;
                        }
                    }
                }
            }

            Ready ready = new Ready(scanner, random, gameManager);
            ready.runReady();
        }
    }

    private void displayTeamRecruits() {
        System.out.printf("""
                [==================]
                 | Team Recruiter |
                [==================]
                
                >> Your current party size is %d.
                
                >> You need a team of 2 to compete in the arena.
                
                >> Choose a recruit to add to your team.
                
                [Recruits]
                """, gameManager.getPlayerParty().size());

        for (int i = 0; i < 3; i++) {
            System.out.print("[=======================] ");
        }

        System.out.println();

        for (Unit recruit : gameManager.getTeamRecruits()) {
            System.out.printf(" | %-19s |  ", recruit.getOriginalName());
        }

        System.out.println();

        for (Unit recruit : gameManager.getTeamRecruits()) {
            System.out.printf(" |   HP: %-4d / %-4d   |  ", recruit.getCurrentHP(), recruit.getMaximumHP());
        }

        System.out.println();

        for (Unit recruit : gameManager.getTeamRecruits()) {
            System.out.printf(" | LVL: %-4d ATK: %-4d |  ", recruit.getLevel(), recruit.getAtk());
        }

        System.out.println();

        for (Unit recruit : gameManager.getTeamRecruits()) {
            System.out.printf(" | DEF: %-4d SPD: %-4d |  ", recruit.getDef(), recruit.getSpd());
        }

        System.out.println();

        for (int i = 0; i < 3; i++) {
            System.out.print("[=======================] ");
        }

        if (!gameManager.getPlayerParty().isEmpty()) {
            System.out.println();
            System.out.println();
        }

        gameManager.displayPlayerParty();

        System.out.println("\n");

        int i = 1;
        for (Unit recruit : gameManager.getTeamRecruits()) {
            System.out.printf("[%d] = %s\n", i, recruit.getOriginalName());
            i++;
        }

        System.out.println();
    }

    private void displayTeamRecruitConfirmation(String chosenRecruitName) {
        System.out.printf("""
                [==================]
                 | Team Recruiter |
                [==================]
                
                >> You have decided to recruit [%s].
                
                >> Is this okay?
                
                [1] = Yes
                [2] = No
                
                """, chosenRecruitName);
    }
}