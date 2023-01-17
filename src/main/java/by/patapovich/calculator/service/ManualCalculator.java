package by.patapovich.calculator.service;

import by.patapovich.calculator.util.ByteUtil;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class ManualCalculator implements Calculator {
    @Override
    public byte[] calculateExpressions(byte[] expressions) {
        List<String> result = new ArrayList<>();
        for (String expr : ByteUtil.byteArayToStringList(expressions)) {
            result.add(String.valueOf(calculate(expr)));
        }
        return ByteUtil.stringListToByteArray(result);
    }

    public double calculate(String expression) {
        String postfixExpression = getPostfixString(expression);
        Deque<Double> locals = new ArrayDeque<>();

        for (int i = 0; i < postfixExpression.length(); i++) {
            char current = postfixExpression.charAt(i);
            if (current == ',')
                current = '.';
            if (Character.isDigit(current) || current == '.') {
                String number = getStringNumber(postfixExpression, i);
                i += number.length() - 1;
                locals.push(Double.valueOf(number));
            } else if (operationPriority.containsKey(current)) {

                if (current == '~') {
                    double last = locals.size() > 0 ? locals.pop() : 0;
                    locals.push(execute('-', 0, last));
                    continue;
                }
                double second = locals.size() > 0 ? locals.pop() : 0;
                double first = locals.size() > 0 ? locals.pop() : 0;

                if (current == '/' && second == 0)
                    return Double.NaN;

                locals.push(execute(current, first, second));
            }
        }
        return locals.pop();
    }

    private String getPostfixString(String expression) {

        expression += "+0";
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char current = expression.charAt(i);
            if (current == ',')
                current = '.';
            if (Character.isDigit(current) || current == '.') {
                String number = getStringNumber(expression, i);
                i += number.length() - 1;
                result.append(number).append(" ");
            } else if (current == '(')
                stack.push(current);
            else if (current == ')') {
                while (stack.size() > 0 && stack.peek() != '(')
                    result.append(stack.pop());
                stack.pop();
            } else if (operationPriority.containsKey(current)) {
                char operation = current;
                if (operation == '-' && (i == 0 || (i > 1 && operationPriority.containsKey(expression.charAt(i - 1)))))
                    operation = '~';
                while (stack.size() > 0 && (operationPriority.get(stack.peek()) >= operationPriority.get(operation)))
                    result.append(stack.pop());
                stack.push(operation);
            }
        }
        stack.forEach(result::append);

        return result.toString();
    }

    private final Map<Character, Integer> operationPriority = Map.of(
            '(', 0,
            '+', 1,
            '-', 1,
            '*', 2,
            '/', 2,
            '^', 3,
            '~', 4
    );

    private double execute(char op, double first, double second) {
        switch (op) {
            case '+' -> {
                return first + second;
            }
            case '-' -> {
                return first - second;
            }
            case '*' -> {
                return first * second;
            }
            case '/' -> {
                return first / second;
            }
            case '^' -> {
                return Math.pow(first, second);
            }
        }
        return 0;
    }

    private String getStringNumber(String expr, int pos) {
        StringBuilder strNumber = new StringBuilder();

        for (; pos < expr.length(); pos++) {
            char num = expr.charAt(pos);
            if (num == ',')
                num = '.';
            if (Character.isDigit(num) || num == '.')
                strNumber.append(num);
            else
                break;
        }

        return strNumber.toString();
    }
}
