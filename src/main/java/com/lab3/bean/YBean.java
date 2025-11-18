package com.lab3.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class YBean implements Serializable {
    private BigDecimal y = new BigDecimal("0");
    private List<Integer> range;


    public YBean() {
        range = new ArrayList<>();
        
        // Генерируем диапазон от -4 до 4
        for (int i = -4; i <= 4; i++) {
            range.add(i);
        }
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public String handleSelection() {
        return null;
    }

    public List<Integer> getPossibleRange() {
        return range;
    }
}
