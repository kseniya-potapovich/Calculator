package by.patapovich.calculator.service;

import by.patapovich.calculator.util.ByteUtil;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;
import java.util.List;

public class ExternalCalculator implements Calculator {

    @Override
    public byte[] calculateExpressions(byte[] expressions) {
        List<String> result = new ArrayList<>();
        for (String expr : ByteUtil.byteArayToStringList(expressions)) {
            result.add(String.valueOf((new Expression(expr)).calculate()));
        }
        return ByteUtil.stringListToByteArray(result);
    }
}
