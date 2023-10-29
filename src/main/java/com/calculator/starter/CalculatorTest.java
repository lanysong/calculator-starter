package com.calculator.starter;

import com.calculator.starter.extern.Calculator;
import com.calculator.starter.extern.CalculatorExtern;
import com.calculator.starter.strategy.PrecisionStrategy;

import java.math.BigDecimal;

public class CalculatorTest {
    public static void main(String[] args) {
        // 创建一个 Calculator 实例，设置精度为 2
        Calculator calculator = new Calculator(new PrecisionStrategy(2));

        // 执行加法操作
        calculator = calculator.add(BigDecimal.valueOf(5));
        BigDecimal result = calculator.getResult();
        System.out.println("Result after addition: " + result);

        // 执行减法操作
        calculator = calculator.subtract(BigDecimal.valueOf(2.5));
        result = calculator.getResult();
        System.out.println("Result after subtraction: " + result);

        // 执行乘法操作
        calculator = calculator.multiply(BigDecimal.valueOf(3));
        result = calculator.getResult();
        System.out.println("Result after multiplication: " + result);

        // 执行除法操作
        calculator = calculator.divide(BigDecimal.valueOf(4));
        result = calculator.getResult();
        System.out.println("Result after division: " + result);

        // 撤销上一次操作
        calculator = calculator.undo();
        result = calculator.getResult();
        System.out.println("Result after undo: " + result);

        // 重做上一次撤销的操作
        calculator = calculator.redo();
        result = calculator.getResult();
        System.out.println("Result after redo: " + result);

        // 更改精度并重新应用加法操作
        calculator = calculator.withPrecision(3);
        calculator = calculator.add(BigDecimal.valueOf(1.345));
        result = calculator.getResult();
        System.out.println("Result after changing precision: " + result);


        Calculator calculatorExtern = new Calculator(new PrecisionStrategy(2));
        BigDecimal result1 = CalculatorExtern
                .using(calculatorExtern)
                .perform(CalculatorExtern.add(BigDecimal.valueOf(5)))
                .perform(CalculatorExtern.subtract(BigDecimal.valueOf(2.5)))
                .perform(CalculatorExtern.multiply(BigDecimal.valueOf(3)))
                .perform(CalculatorExtern.divide(BigDecimal.valueOf(4)))
                .getResult();

        System.out.println("Final Result: " + result1);
    }
}
