package by.patapovich.calculator;

import by.patapovich.calculator.console.ConsoleCalculator;
import by.patapovich.calculator.service.EncryptionService;
import by.patapovich.calculator.service.ExternalCalculator;
import by.patapovich.calculator.service.ManualCalculator;

import java.util.Scanner;

public class ConsoleCalculatorApplication {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ConsoleCalculator manualCalculator = new ConsoleCalculator(new ManualCalculator());
        ConsoleCalculator externalCalculator = new ConsoleCalculator(new ExternalCalculator());
        EncryptionService encryptionService = new EncryptionService();
        while (true) {
            System.out.println("Choose your destiny: ");
            System.out.println("1. Read expressions from file manually");
            System.out.println("2. Read expressions from file with external library");
            System.out.println("3. Encrypt file");
            System.out.println("4. Decrypt file");
            System.out.println("5. Zip file");
            System.out.println("6. Unzip file");
            System.out.println("0. Quit program");

            int number = scanner.nextInt();
            switch (number) {
                case 0 -> {
                    return;
                }
                case 1 -> manualCalculator.fileReadingCalculation();
                case 2 -> externalCalculator.fileReadingCalculation();
                case 3 -> encryptionService.encryptFile();
                case 4 -> encryptionService.decryptFile();
                default -> System.out.println("Incorrect menu choose");
            }
        }
    }

}
