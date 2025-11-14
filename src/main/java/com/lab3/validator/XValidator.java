package com.lab3.validator;

import java.math.BigDecimal;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.component.UIComponent;

@FacesValidator("xValidator")
public class XValidator implements Validator<BigDecimal> {

    private static final BigDecimal MIN_VALUE = new BigDecimal("-3.0");
    private static final BigDecimal MAX_VALUE = new BigDecimal("5.0");

    @Override
    public void validate(FacesContext context, UIComponent component, BigDecimal value) throws ValidatorException {
        if (value == null) {
            return;
        }

        if (value.compareTo(MIN_VALUE) < 0 || value.compareTo(MAX_VALUE) > 0) {
            FacesMessage message = new FacesMessage(
                    "Значение должно быть в пределах от " + MIN_VALUE + " до " + MAX_VALUE);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
