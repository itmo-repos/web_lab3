package com.lab3.bean;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.lab3.entity.ResultEntity;

import jakarta.ejb.EJB;

public class ResultsWrapperBean {
    @EJB
    private ResultsControllerBean results;

    public void addResult(BigDecimal x, BigDecimal y, BigDecimal r, boolean hit) {
        results.addResult(x, y, r, hit);
    }

    public void addResult(BigDecimal x, BigDecimal y, BigDecimal r) {
        results.addResult(x, y, r);
    }

    public ArrayList<ResultEntity> getResults() {
        return results.getResults();
    }
    
}
