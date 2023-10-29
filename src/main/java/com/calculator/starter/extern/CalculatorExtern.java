package com.calculator.starter.extern;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * 拓展类
 */
public class CalculatorExtern {
    private final Calculator calculator;

    public CalculatorExtern(Calculator calculator) {
        this.calculator = calculator;
    }

    public CalculatorExtern perform(Function<Calculator, Calculator> operation) {
        return new CalculatorExtern(operation.apply(calculator));
    }

    public BigDecimal getResult() {
        return calculator.getResult();
    }

    // 静态工厂方法，用于创建DSL实例并关联到Calculator
    public static CalculatorExtern using(Calculator calculator) {
        return new CalculatorExtern(calculator);
    }

    // 静态工厂方法，用于创建加法操作
    public static Function<Calculator, Calculator> add(BigDecimal operand) {
        return calculator -> calculator.add(operand);
    }

    // 静态工厂方法，用于创建减法操作
    public static Function<Calculator, Calculator> subtract(BigDecimal operand) {
        return calculator -> calculator.subtract(operand);
    }

    // 静态工厂方法，用于创建乘法操作
    public static Function<Calculator, Calculator> multiply(BigDecimal operand) {
        return calculator -> calculator.multiply(operand);
    }

    // 静态工厂方法，用于创建除法操作
    public static Function<Calculator, Calculator> divide(BigDecimal operand) {
        return calculator -> calculator.divide(operand);
    }

    // 静态工厂方法，用于设置精度
    public static Function<Calculator, Calculator> withPrecision(int scale) {
        return calculator -> calculator.withPrecision(scale);
    }
}
