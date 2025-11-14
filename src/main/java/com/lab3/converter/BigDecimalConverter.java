package com.lab3.converter;

import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.faces.context.FacesContext;
import jakarta.faces.component.UIComponent;

import java.math.BigDecimal;

@FacesConverter("bigDecimalConverter")
public class BigDecimalConverter implements Converter<BigDecimal> {

    @Override
    public BigDecimal getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            try {
                return new BigDecimal(value.replace(",", "."));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, BigDecimal value) {
        if (value != null) {
            return value.toString();
        }
        return "";
    }
}
