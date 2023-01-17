package by.patapovich.calculator.decorator;

import by.patapovich.calculator.persistence.DataSource;

public class DataSourceDecorator implements DataSource {
    private final DataSource wrap;
    public DataSourceDecorator(DataSource source) {
        this.wrap = source;
    }

    @Override
    public byte[] read() {
        return wrap.read();
    }

    @Override
    public void write(byte[] data) {
        wrap.write(data);
    }
}
