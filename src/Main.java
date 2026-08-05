import objects.GameManager;
import screens.Title;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        GameManager gameManager = new GameManager(random);

        Title title = new Title(scanner, random, gameManager);
        title.runTitle();

        scanner.close();
    }
}
