package com.lab3.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class XBean implements Serializable {
    private BigDecimal x = new BigDecimal("0.0");

    public XBean() {
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

}
