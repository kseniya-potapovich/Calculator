package by.patapovich.calculator.console;

import by.patapovich.calculator.persistence.DataSource;
import by.patapovich.calculator.builder.FileBuilder;
import by.patapovich.calculator.service.Calculator;

public class ConsoleCalculator {
    private final Calculator calculator;
    public ConsoleCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    public void fileReadingCalculation() {
        FileBuilder fileBuilder = FileBuilder.getInstance();
        System.out.println("Configure input file:");
        DataSource inputFile = fileBuilder.buildInputFile();
        System.out.println("Configure output file:");
        DataSource outputFile = fileBuilder.buildOutputFile();
        outputFile.write(calculator.calculateExpressions(inputFile.read()));
    }
}