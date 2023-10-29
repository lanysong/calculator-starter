package com.calculator.starter.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 精度策略 可自定义
 */
public class PrecisionStrategy {
    private final int scale; // 精度，小数点后的位数

    public PrecisionStrategy(int scale) {
        this.scale = scale;
    }

    // 获取当前精度设置
    public int getScale() {
        return scale;
    }

    // 应用精度，将结果四舍五入到指定精度
    public BigDecimal applyPrecision(BigDecimal result) {
        return result.setScale(scale, RoundingMode.HALF_UP);
    }
}
