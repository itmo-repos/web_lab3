package com.lab3.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Locale;
import java.util.ResourceBundle;


public class AreaHitChecker {
    private static final MathContext MATH_CONTEXT = new MathContext(50);

    private AreaHitChecker() {

    }
    
    public static String validateParameters(BigDecimal xBD, BigDecimal yBD, BigDecimal rBD) {
        return validateParameters(xBD, yBD, rBD, Locale.getDefault());
    }

    public static String validateParameters(BigDecimal xBD, BigDecimal yBD, BigDecimal rBD, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        if (xBD.abs(MATH_CONTEXT).compareTo(new BigDecimal("6.0", MATH_CONTEXT)) > 0) {
            return bundle.getString("error.validation.x");
        }

        if (yBD.abs(MATH_CONTEXT).compareTo(new BigDecimal("6.0", MATH_CONTEXT)) > 0) {
            return bundle.getString("error.validation.y");
        }

        if (rBD.compareTo(new BigDecimal("1")) < 0 || rBD.compareTo(new BigDecimal("5")) > 0) {
            return bundle.getString("error.validation.r");
        }

        return null;
    }

    public static boolean checkHit(BigDecimal xBD, BigDecimal yBD, BigDecimal rBD) {
        BigDecimal half = new BigDecimal("0.5", MATH_CONTEXT);
        
        if (xBD.signum() > 0 && yBD.signum() > 0) {
            
            return false;
        } else if (xBD.signum() <= 0 && yBD.signum() >= 0) {

            BigDecimal rHalf = rBD.multiply(half, MATH_CONTEXT);
            BigDecimal negX = xBD.negate();
            
            return yBD.compareTo(rHalf) <= 0 && negX.compareTo(rBD) <= 0;
        } else if (xBD.signum() < 0 && yBD.signum() < 0) {

            BigDecimal xSquared = xBD.multiply(xBD, MATH_CONTEXT);
            BigDecimal ySquared = yBD.multiply(yBD, MATH_CONTEXT);
            BigDecimal rHalf = rBD.multiply(half, MATH_CONTEXT);
            BigDecimal rHalfSquared = rHalf.multiply(rHalf, MATH_CONTEXT);
            BigDecimal sum = xSquared.add(ySquared, MATH_CONTEXT);

            return sum.compareTo(rHalfSquared) <= 0;
        } else {
            BigDecimal negY = yBD.negate();

            BigDecimal maxY = rBD.subtract(xBD.multiply(new BigDecimal("2.0"), MATH_CONTEXT), MATH_CONTEXT);

            return negY.compareTo(maxY) <= 0;
        }
    }
    
    
}
