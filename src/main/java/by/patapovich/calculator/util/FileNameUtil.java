package by.patapovich.calculator.util;

import java.util.Scanner;

public class FileNameUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static String inputFileName() {
        System.out.println("Input file name: ");
        return scanner.nextLine();
    }

    public static String outputFileName() {
        System.out.println("Input file name: ");
        return scanner.nextLine();
    }
}
