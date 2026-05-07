package com.lab3.mbean;

public interface MissRatioMBean {
    double getMissRatio();
    int getTotalClicks();
    int getMisses();
    void reset();
    void addClick(boolean hit);
}
