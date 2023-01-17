package by.patapovich.calculator.decorator;

import by.patapovich.calculator.exception.CustomException;
import by.patapovich.calculator.persistence.DataSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import by.patapovich.calculator.util.ByteUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class XMLDecorator extends DataSourceDecorator {
    public XMLDecorator(DataSource source) {super(source);}

    @Override
    public byte[] read() {
        ObjectMapper xmlMapper = new XmlMapper();
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(xmlMapper.readValue(new String(super.read()), List.class));
            return baos.toByteArray();
        } catch (IOException e) {
            throw new CustomException("Wrong read XML data");
        }
    }

    private byte[] byteToXml(byte[] data) {
        try {
            ObjectMapper mapper = new XmlMapper();
            return mapper.writeValueAsBytes(ByteUtil.byteArayToStringList(data));
        } catch (JsonProcessingException e) {
            throw new CustomException("Error while map XML data");
        }
    }

    @Override
    public void write(byte[] data) {
        super.write(byteToXml(data));
    }

}

