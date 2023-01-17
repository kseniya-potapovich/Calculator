package by.patapovich.calculator;

import by.patapovich.calculator.builder.FileBuilder;
import by.patapovich.calculator.data.TestDataSource;
import by.patapovich.calculator.persistence.DataSource;
import by.patapovich.calculator.persistence.FileType;
import by.patapovich.calculator.service.ExternalCalculator;
import by.patapovich.calculator.service.ManualCalculator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    private final ManualCalculator manualCalculator = new ManualCalculator();

    private final ExternalCalculator externalCalculator = new ExternalCalculator();

    private static final FileBuilder fileBuilder = FileBuilder.getInstance();

    @Test
    void testDifferentExpressions() {
        List<String> expressions = List.of(
                "15/(7-(1+1))*3-(2+(1+1))*15/(7-(200+1))*3-(2+(1+1))*(15/(7-(1+1))*3-(2+(1+1))+15/(7-(1+1))*3-(2+(1+1)))",
                "((12 - 10) + 124 / 567) + 1 - 2",
                "1234 / 500 + 543",
                "((3 + 3 + 3 + 3 + 3 + 3) / 3) * 3",
                "100 / 0",
                "((12 - 10) + 124 / 0) + 1 - 2",
                "4 - 1",
                "5 / 2",
                "(1 + 1) / 2",
                "(1 + 1) / (1 + 1)",
                "12 / (2 + 2)",
                "2.2 / 2",
                "(1.3 * 5 + 8) / 5",
                "(1467*43+2334/2-132*2+900/455+654389-15*10-59+15037)+2*6.5",
                "(146.7*43+2334/2-132*2+90.0/455+654389-15*10-59+150.37)+2*6.5"
        );

        assertEquals(-30.072164948453608, manualCalculator.calculate(expressions.get(0)));
        assertEquals(1.2186948853615522, manualCalculator.calculate(expressions.get(1)));
        assertEquals(545.468, manualCalculator.calculate(expressions.get(2)));
        assertEquals(18.0, manualCalculator.calculate(expressions.get(3)));
        assertEquals(Double.NaN, manualCalculator.calculate(expressions.get(4)));
		assertEquals(Double.NaN, manualCalculator.calculate(expressions.get(5)));
        assertEquals(3.0, manualCalculator.calculate(expressions.get(6)));
        assertEquals(2.5, manualCalculator.calculate(expressions.get(7)));
        assertEquals(1.0, manualCalculator.calculate(expressions.get(8)));
        assertEquals(1.0, manualCalculator.calculate(expressions.get(9)));
        assertEquals(3.0, manualCalculator.calculate(expressions.get(10)));
        assertEquals(1.1, manualCalculator.calculate(expressions.get(11)));
        assertEquals(2.9, manualCalculator.calculate(expressions.get(12)));
        assertEquals(733215.978021978, manualCalculator.calculate(expressions.get(13)));
        assertEquals(661554.6678021977, manualCalculator.calculate(expressions.get(14)));
    }

    @Test
    void externalJsonToPlain() {
        DataSource inputFile = fileBuilder.setFileName("test/input.json").setFileType(FileType.JSON).build();
        DataSource output = fileBuilder.setDataSource(new TestDataSource(externalCalculator.calculateExpressions(inputFile.read()))).build();
        DataSource outputFile = fileBuilder.setFileName("test/output.txt").setFileType(FileType.PLAIN).build();
        assertArrayEquals(outputFile.read(), output.read());
    }

    @Test
    void manualJsonToPlain() {
        DataSource inputFile = fileBuilder.setFileName("test/input.json").setFileType(FileType.JSON).build();
        DataSource output = fileBuilder.setDataSource(new TestDataSource(manualCalculator.calculateExpressions(inputFile.read()))).build();
        DataSource outputFile = fileBuilder.setFileName("test/output.txt").setFileType(FileType.PLAIN).build();
        assertArrayEquals(outputFile.read(), output.read());
    }

    @Test
    void externalEncryptedJsonToPlain() {
        FileBuilder fileBuilder = FileBuilder.getInstance();
        DataSource inputFile = fileBuilder.setFileName("test/encrypted.json").setEncrypt(true).setFileType(FileType.JSON).build();
        DataSource output = fileBuilder.setDataSource(new TestDataSource(externalCalculator.calculateExpressions(inputFile.read()))).build();
        DataSource outputFile = fileBuilder.setFileName("test/output.txt").setFileType(FileType.PLAIN).build();
        assertArrayEquals(outputFile.read(), output.read());
    }

    @Test
    void manualEncryptedJsonToPlain() {
        FileBuilder fileBuilder = FileBuilder.getInstance();
        DataSource inputFile = fileBuilder.setFileName("test/encrypted.json").setEncrypt(true).setFileType(FileType.JSON).build();
        DataSource output = fileBuilder.setDataSource(new TestDataSource(manualCalculator.calculateExpressions(inputFile.read()))).build();
        DataSource outputFile = fileBuilder.setFileName("test/output.txt").setFileType(FileType.PLAIN).build();
        assertArrayEquals(outputFile.read(), output.read());
    }

    @Test
    void externalXMLToPlain() {
        FileBuilder fileBuilder = FileBuilder.getInstance();
        DataSource inputFile = fileBuilder.setFileName("test/input.xml").setFileType(FileType.XML).build();
        DataSource result = fileBuilder.setDataSource(new TestDataSource(externalCalculator.calculateExpressions(inputFile.read()))).build();
        DataSource outputFile = fileBuilder.setFileName("test/output.txt").setFileType(FileType.PLAIN).build();
        assertArrayEquals(outputFile.read(), result.read());
    }

    @Test
    void manualXMLToPlain() {
        FileBuilder fileBuilder = FileBuilder.getInstance();
        DataSource inputFile = fileBuilder.setFileName("test/input.xml").setFileType(FileType.XML).build();
        DataSource result = fileBuilder.setDataSource(new TestDataSource(manualCalculator.calculateExpressions(inputFile.read()))).build();
        DataSource outputFile = fileBuilder.setFileName("test/output.txt").setFileType(FileType.PLAIN).build();
        assertArrayEquals(outputFile.read(), result.read());
    }

}
