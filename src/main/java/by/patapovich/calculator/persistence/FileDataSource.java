package by.patapovich.calculator.persistence;

import by.patapovich.calculator.exception.CustomException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDataSource implements DataSource {

    private final String fileName;

    public FileDataSource(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public byte[] read() {
        File file = new File("files/"+ fileName);
        try (FileInputStream fl = new FileInputStream(file))
        {
            byte[] arr = new byte[(int)file.length()];
            fl.read(arr);
            return arr;
        } catch (IOException e) {
            throw new CustomException("Error while read file");
        }

    }

    @Override
    public void write(byte[] data) {
        try(FileOutputStream outputStream = new FileOutputStream("files/" + fileName))
         {
             outputStream.write(data);
        } catch (IOException e) {
            throw new CustomException("Error while write file");
        }
    }
}
