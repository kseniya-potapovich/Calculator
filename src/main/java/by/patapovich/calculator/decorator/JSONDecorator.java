package by.patapovich.calculator.decorator;

import by.patapovich.calculator.exception.CustomException;
import by.patapovich.calculator.persistence.DataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import by.patapovich.calculator.util.ByteUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class JSONDecorator extends DataSourceDecorator {
    public JSONDecorator(DataSource source) {
        super(source);
    }

    private byte[] byteToJson(byte[] data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsBytes(ByteUtil.byteArayToStringList(data));
        } catch (JsonProcessingException e) {
            throw new CustomException("Error while map JSON data");
        }
    }

    @Override
    public byte[] read() {
        ObjectMapper mapper = new ObjectMapper();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(mapper.readValue(new String(super.read()), List.class));
            return baos.toByteArray();
        } catch (IOException e) {
            throw new CustomException("Wrong read JSON data");
        }
    }

    @Override
    public void write(byte[] data) {
        super.write(byteToJson(data));
    }
}
