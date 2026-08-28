package ui;

import java.util.Scanner;

public class Ui {

    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readInput() {
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void close() {
        scanner.close();
    }


}