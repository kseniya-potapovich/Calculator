package by.patapovich.calculator.decorator;

import by.patapovich.calculator.exception.CustomException;
import by.patapovich.calculator.persistence.DataSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipDecorator extends DataSourceDecorator {

    private String entryName;

    public ZipDecorator(DataSource source, String entryName) {
        super(source);
        this.entryName = entryName;
    }

    public ZipDecorator(DataSource source) {
        super(source);
    }

    private byte[] archive(byte[] data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            ZipEntry entry = new ZipEntry(entryName);
            entry.setSize(data.length);
            zos.putNextEntry(entry);
            zos.write(data);
            zos.closeEntry();
        } catch (IOException e) {
            throw new CustomException("Error while zip file");
        }

        return baos.toByteArray();
    }

    private byte[] unzipByteArray(byte[] byteArray) {
        try (InputStream inputStream = new ByteArrayInputStream(byteArray);
             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            if ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    byte[] buffer = new byte[1024];
                    int len;
                    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                        while ((len = zipInputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, len);
                        }
                        return outputStream.toByteArray();
                    }
                }
            }
        } catch (IOException e) {
            throw new CustomException("Error while unzip file");
        }
        return byteArray;
    }

    @Override
    public byte[] read() {
        return unzipByteArray(super.read());
    }

    @Override
    public void write(byte[] data) {
        super.write(archive(data));
    }
}
