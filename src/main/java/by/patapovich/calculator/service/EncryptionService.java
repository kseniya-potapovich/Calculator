package by.patapovich.calculator.service;


import by.patapovich.calculator.decorator.DataSourceDecorator;
import by.patapovich.calculator.decorator.EncryptionDecorator;
import by.patapovich.calculator.persistence.FileDataSource;
import by.patapovich.calculator.util.FileNameUtil;

public class EncryptionService {

    public void encryptFile() {
        FileDataSource input = new FileDataSource(FileNameUtil.inputFileName());
        DataSourceDecorator fileDecorator = new EncryptionDecorator(new DataSourceDecorator(new FileDataSource(FileNameUtil.outputFileName())));
        fileDecorator.write(input.read());
    }

    public void decryptFile() {
        DataSourceDecorator fileDecorator =new EncryptionDecorator(new FileDataSource(FileNameUtil.inputFileName()));
        FileDataSource output = new FileDataSource(FileNameUtil.outputFileName());
        output.write(fileDecorator.read());
    }
}
