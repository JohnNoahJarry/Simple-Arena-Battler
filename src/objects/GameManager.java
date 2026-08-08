package objects;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class GameManager {
    Random random;

    String teamName;
    ArrayList<Unit> playerParty;
    ArrayList<Unit> teamRecruits;
    String enemyTeamName;
    ArrayList<Unit> enemyParty;
    ArrayList<String> names;

    int currentBattleNumber;
    String currentDifficulty;

    boolean isGameOver;

    public GameManager(Random random) {
        this.random = random;

        this.playerParty = new ArrayList<>();
        this.teamRecruits = new ArrayList<>();
        this.enemyParty = new ArrayList<>();

        this.names = new ArrayList<>();

        InputStream inputStream = getClass().getResourceAsStream("/Names");
        assert inputStream != null;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line;

            while ((line = reader.readLine()) != null) {
                names.add(line.trim());
            }
        }
        catch (IOException e) {
            System.out.println("Error: Something went wrong.");
        }

        this.currentBattleNumber = 1;

        this.isGameOver = false;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void createTeamRecruits() {
        teamRecruits.clear();

        for (int i = 0; i < 3; i++) {
            String name = names.get(random.nextInt(names.size()));

            int level = this.currentBattleNumber + 19;
            int hpGrowth = 6;
            int atkGrowth = 1;
            int defGrowth = 1;
            int spdGrowth = 1;

            for (int j = 0; j < 4; j++) {
                int randomNumber = random.nextInt(1,5);

                switch (randomNumber) {
                    case 1 -> hpGrowth++;
                    case 2 -> atkGrowth++;
                    case 3 -> defGrowth++;
                    case 4 -> spdGrowth++;
                }
            }

            this.teamRecruits.add(new Unit(name, level, hpGrowth, atkGrowth, defGrowth, spdGrowth));
        }
    }

    public ArrayList<Unit> getTeamRecruits() {
        return this.teamRecruits;
    }

    public ArrayList<Unit> getPlayerParty() {
        return this.playerParty;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public int getCurrentBattleNumber() {
        return this.currentBattleNumber;
    }

    public void setCurrentDifficulty(String difficulty) {
        this.currentDifficulty = difficulty;
    }

    public String getCurrentDifficulty() {
        return this.currentDifficulty;
    }

    public boolean getIsGameOver() {
        return this.isGameOver;
    }

    public void createEnemyParty() {
        enemyParty.clear();

        this.enemyTeamName = this.names.get(random.nextInt(names.size()));

        for (int i = 0; i < 2; i++) {
            String name = this.names.get(random.nextInt(names.size()));

            int level = 1;

            switch (this.currentDifficulty) {
                case "1" -> level = this.currentBattleNumber + 19;
                case "2" -> level = this.currentBattleNumber * 2 + 20;
                case "3" -> level = this.currentBattleNumber * 4 + 20;
            }

            int hpGrowth = 6;
            int atkGrowth = 1;
            int defGrowth = 1;
            int spdGrowth = 1;

            for (int j = 0; j < 4; j++) {
                int randomNumber = random.nextInt(1,5);

                switch (randomNumber) {
                    case 1 -> hpGrowth++;
                    case 2 -> atkGrowth++;
                    case 3 -> defGrowth++;
                    case 4 -> spdGrowth++;
                }
            }

            this.enemyParty.add(new Unit(name, level, hpGrowth, atkGrowth, defGrowth, spdGrowth));
        }
    }

    public void createBoss() {
        enemyParty.clear();

        this.enemyTeamName = "Champion";

        for (int i = 0; i < 2; i++) {
            String name = this.names.get(random.nextInt(names.size()));

            int level = 100;

            int hpGrowth = 6;
            int atkGrowth = 1;
            int defGrowth = 1;
            int spdGrowth = 1;

            for (int j = 0; j < 4; j++) {
                int randomNumber = random.nextInt(1,5);

                switch (randomNumber) {
                    case 1 -> hpGrowth++;
                    case 2 -> atkGrowth++;
                    case 3 -> defGrowth++;
                    case 4 -> spdGrowth++;
                }
            }

            this.enemyParty.add(new Unit(name, level, hpGrowth, atkGrowth, defGrowth, spdGrowth));
        }
    }

    public String getEnemyTeamName() {
        return this.enemyTeamName;
    }

    public ArrayList<Unit> getEnemyParty() {
        return this.enemyParty;
    }

    public void prepareEnemyParty() {
        for (Unit enemy : this.enemyParty) {
            if (!enemy.getStatus().equals("Defeated")) {
                int actionRNG = random.nextInt(1, 4);

                if (actionRNG == 3) {
                    enemy.setStatus("Defending");
                }
                else {
                    enemy.setStatus("Attacking");

                    int targetRNG = random.nextInt(2);
                    enemy.setTarget(this.playerParty.get(targetRNG));
                    while (enemy.getTarget().getStatus().equals("Defeated")) {
                        targetRNG = random.nextInt(2);
                        enemy.setTarget(this.playerParty.get(targetRNG));
                    }

                    int magicRNG = random.nextInt(1,101);

                    if (magicRNG <= enemy.getSunChance()) {
                        enemy.setSelectedMagic("Sun");
                    }
                    else if (magicRNG <= enemy.getSunChance()+enemy.getStarChance()) {
                        enemy.setSelectedMagic("Star");
                    }
                    else {
                        enemy.setSelectedMagic("Moon");
                    }
                }
            }
        }
    }

    public ArrayList<String> executeActions() {
        ArrayList<Unit> allUnitsInBattle = new ArrayList<>();
        ArrayList<String> messages = new ArrayList<>();

        allUnitsInBattle.addAll(this.playerParty);
        allUnitsInBattle.addAll(this.enemyParty);

        allUnitsInBattle.sort(Comparator.comparing(Unit::getSpd).reversed());

        for (Unit unit : allUnitsInBattle) {
            if (unit.getStatus().equals("Attacking")) {
                String message = unit.attackTarget();
                messages.add(message);
            }
        }

        return messages;
    }

    public void rollEnemyPartyMagicChances() {
        for (Unit enemy : this.enemyParty) {
            if (!enemy.getStatus().equals("Defeated")) {
                int sunChance = 0;
                int starChance = 0;
                int moonChance = 0;

                for (int i = 0; i < 5; i++) {
                    int chanceRNG = random.nextInt(1,4);
                    switch (chanceRNG) {
                        case 1 -> sunChance += 20;
                        case 2 -> starChance += 20;
                        case 3 -> moonChance += 20;
                    }
                }

                enemy.setSunChance(sunChance);
                enemy.setStarChance(starChance);
                enemy.setMoonChance(moonChance);
            }
        }
    }

    public void setIsGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public void cleanPlayerParty() {
        for (int i = 1; i >= 0; i--) {
            if (this.playerParty.get(i).getStatus().equals("Defeated")) {
                this.playerParty.remove(i);
            }
        }
    }

    public void reset() {
        this.playerParty = new ArrayList<>();
        this.teamRecruits = new ArrayList<>();
        this.enemyParty = new ArrayList<>();

        this.currentBattleNumber = 1;
    }

    public void levelUpPlayerParty() {
        for (Unit player : this.playerParty) {
            switch (currentDifficulty) {
                case "1" -> {
                    for (int i = 0; i < 2; i++) {
                        player.levelUp();
                    }
                }
                case "2" -> {
                    for (int i = 0; i < 4; i++) {
                        player.levelUp();
                    }
                }
                case "3" -> {
                    for (int i = 0; i < 8; i++) {
                        player.levelUp();
                    }
                }
            }
        }
    }

    public void incrementCurrentBattleNumber() {
        this.currentBattleNumber++;
    }

    public void displayPlayerParty() {
        if (!this.playerParty.isEmpty()) {
            System.out.printf("[Team %s]\n", this.teamName);

            for (int i = 0; i < this.playerParty.size(); i++) {
                System.out.print("[==========================] ");
            }

            System.out.println();

            for (Unit player : this.playerParty) {
                System.out.printf(" | %-22s |  ", player.getName());
            }

            System.out.println();

            for (Unit player : this.playerParty) {
                System.out.printf(" |    HP: %-4d / %-4d     |  ", player.getCurrentHP(), player.getMaximumHP());
            }

            System.out.println();

            for (Unit player : this.playerParty) {
                System.out.printf(" |   LVL: %-4d ATK: %-4d  |  ", player.getLevel(), player.getAtk());
            }

            System.out.println();

            for (Unit player : this.playerParty) {
                System.out.printf(" |   DEF: %-4d SPD: %-4d  |  ", player.getDef(), player.getSpd());
            }

            System.out.println();

            for (int i = 0; i < this.playerParty.size(); i++) {
                System.out.print("[==========================] ");
            }
        }
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public ArrayList<String> getNames() {
        return this.names;
    }
}
