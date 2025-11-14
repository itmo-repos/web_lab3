package com.lab3.bean;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.validator.ValidatorException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class RBean implements Serializable {
    private BigDecimal r = new BigDecimal("0.0");

    private List<BigDecimal> variantsList;

    public RBean() {
        variantsList = Arrays.asList(
            new BigDecimal(1),
            new BigDecimal(2),
            new BigDecimal(3),
            new BigDecimal(4),
            new BigDecimal(5)
        );
    }

    public List<BigDecimal> getVariantsList () {
        return variantsList;
    }

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }

    public void validateXBeanValue(Object o) {
        if (o == null) {
            FacesMessage message = new FacesMessage("Input X!");
            throw new ValidatorException(message);
        }
    }
}
