package screens;

import objects.GameManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TeamName {
    Scanner scanner;
    Random random;
    GameManager gameManager;

    public TeamName(Scanner scanner, Random random, GameManager gameManager) {
        this.scanner = scanner;
        this.random = random;
        this.gameManager = gameManager;
    }

    public void runTeamCreator() {
        while (true) {
            gameManager.clearScreen();
            displayTeamNameSelection();
            System.out.print("<< ");
            String choice = scanner.nextLine();
            String teamName;

            if (choice.isEmpty()) {
                teamName = gameManager.getNames().get(random.nextInt(gameManager.getNames().size()));
            }
            else if (choice.length() > 19) {
                continue;
            }
            else {
                teamName = choice;
            }

            String yesOrNo;

            while (true) {
                gameManager.clearScreen();
                displayTeamNameConfirmation(teamName);
                System.out.print("<< ");
                yesOrNo = scanner.nextLine();

                if (yesOrNo.equals("1")) {
                    gameManager.setTeamName(teamName);
                    break;
                }
                else if (yesOrNo.equals("2")) {
                    break;
                }
            }

            if (yesOrNo.equals("1")) {
                break;
            }
        }

        TeamRecruiter teamRecruiter = new TeamRecruiter(scanner, random, gameManager);
        teamRecruiter.runTeamRecruiter();
    }

    private void displayTeamNameSelection() {
        System.out.println("""
                [================]
                 | Team Creator |
                [================]
                
                >> You will assemble a team to defeat the Arena's champions.
                
                >> First, let's give a name to your team.
                
                >> Enter a team name that's not longer than 19 characters.
                
                >> Enter nothing to generate a random team name.
                """);
    }

    private void displayTeamNameConfirmation(String teamName) {
        System.out.printf("""
                [================]
                 | Team Creator |
                [================]
                
                >> The chosen team name is [Team %s].
                
                >> Is this name okay?
                
                [1] = Yes
                [2] = No
                
                """, teamName);
    }
}
