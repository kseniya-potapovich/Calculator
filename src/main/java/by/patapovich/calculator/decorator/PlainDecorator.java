package by.patapovich.calculator.decorator;

import by.patapovich.calculator.exception.CustomException;
import by.patapovich.calculator.persistence.DataSource;
import by.patapovich.calculator.util.ByteUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlainDecorator extends DataSourceDecorator {
    public PlainDecorator(DataSource source) {
        super(source);
    }

    @Override
    public byte[] read() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            List<String> strings = new ArrayList<>();
            String[] input = new String(super.read()).split("\n");
            Collections.addAll(strings, input);
            oos.writeObject(strings);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new CustomException("Wrong read plain data");
        }
    }

    @Override
    public void write(byte[] data) {
        super.write(String.join("\n", ByteUtil.byteArayToStringList(data)).getBytes());
    }
}
