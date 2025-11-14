package com.lab3.bean;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.validator.ValidatorException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

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

    public void validateXBeanValue(Object o) {
        if (o == null) {
            FacesMessage message = new FacesMessage("Input X!");
            throw new ValidatorException(message);
        }
    }
}
