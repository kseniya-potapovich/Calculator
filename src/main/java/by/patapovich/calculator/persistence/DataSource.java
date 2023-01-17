package by.patapovich.calculator.persistence;

public interface DataSource {
    byte[] read();
    void write(byte[] data);
}
