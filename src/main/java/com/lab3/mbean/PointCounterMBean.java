package com.lab3.mbean;

import java.math.BigDecimal;

public interface PointCounterMBean {
    int getTotalPoints();
    int getHitPoints();
    void reset();
    void addPoint(boolean hit);
    void checkAndNotifyOutOfBounds(BigDecimal x, BigDecimal y, BigDecimal r);
}
