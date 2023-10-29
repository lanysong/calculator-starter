package com.calculator.starter.extern;

import com.calculator.starter.strategy.PrecisionStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.BinaryOperator;

/**
 * 两个数的加、减、乘、除运算，并可以进行undo和redo操作
 * 1、数据类型选择BigDecimal保证精度
 * 2、加、减、乘、除、undo redo 可以抽成策执行 方便自定义计算规则
 */
public class Calculator {
    // 历史操作记录栈，用于存储执行过的操作结果
    private final Deque<BigDecimal> history;

    // 未来操作记录栈，用于支持undo和redo操作
    private final Deque<BigDecimal> future;

    // 精度策略，用于处理计算结果的精度
    private final PrecisionStrategy precisionStrategy;

    // 构造函数，接受精度策略作为参数
    public Calculator(PrecisionStrategy precisionStrategy) {
        this.history = new ArrayDeque<>();
        this.future = new ArrayDeque<>();
        this.precisionStrategy = precisionStrategy;
    }

    // 构造函数，用于内部创建新的Calculator实例，以保持不可变性
    private Calculator(Deque<BigDecimal> history, Deque<BigDecimal> future, PrecisionStrategy precisionStrategy) {
        this.history = history;
        this.future = future;
        this.precisionStrategy = precisionStrategy;
    }

    // 获取当前计算结果，根据精度策略应用精度
    public BigDecimal getResult() {
        return history.isEmpty() ? BigDecimal.ZERO : precisionStrategy.applyPrecision(history.peek());
    }

    // 执行加法操作，并返回新的Calculator实例
    public Calculator add(BigDecimal operand) {
        executeOperation(operand, BigDecimal::add);
        return new Calculator(new ArrayDeque<>(history), new ArrayDeque<>(future), precisionStrategy);
    }

    public Calculator subtract(BigDecimal operand) {
        executeOperation(operand, BigDecimal::subtract);
        return new Calculator(new ArrayDeque<>(history), new ArrayDeque<>(future), precisionStrategy);
    }

    public Calculator multiply(BigDecimal operand) {
        executeOperation(operand, BigDecimal::multiply);
        return new Calculator(new ArrayDeque<>(history), new ArrayDeque<>(future), precisionStrategy);
    }


    public Calculator divide(BigDecimal operand) {
        if (operand.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        executeOperation(operand, (a, b) -> a.divide(b, precisionStrategy.getScale(), RoundingMode.HALF_UP));
        return new Calculator(new ArrayDeque<>(history), new ArrayDeque<>(future), precisionStrategy);
    }

    // 撤销上一次操作
    public Calculator undo() {
        if (!history.isEmpty()) {
            future.push(history.pop());
        }
        return new Calculator(new ArrayDeque<>(history), new ArrayDeque<>(future), precisionStrategy);
    }

    // 重做上一次撤销的操作
    public Calculator redo() {
        if (!future.isEmpty()) {
            history.push(future.pop());
        }
        return new Calculator(new ArrayDeque<>(history), new ArrayDeque<>(future), precisionStrategy);
    }

    // 执行具体操作的核心方法
    private void executeOperation(BigDecimal operand, BinaryOperator<BigDecimal> operation) {
        future.clear();
        BigDecimal result = history.isEmpty() ? operand : operation.apply(history.peek(), operand);
        history.push(result);
    }

    // 设置新的精度策略并返回新的Calculator实例
    public Calculator withPrecision(int scale) {
        PrecisionStrategy customStrategy = new PrecisionStrategy(scale);
        return new Calculator(new ArrayDeque<>(history), new ArrayDeque<>(future), customStrategy);
    }
}
