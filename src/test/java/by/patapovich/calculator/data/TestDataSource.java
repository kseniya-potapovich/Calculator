package by.patapovich.calculator.data;

import by.patapovich.calculator.persistence.DataSource;

public class TestDataSource implements DataSource {
    private byte[] data;

    public TestDataSource(byte[] data) {
        this.data = data;
    }

    @Override
    public byte[] read() {
        return data;
    }

    @Override
    public void write(byte[] data) {
        this.data = data;
    }
}
