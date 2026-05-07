package com.lab3.bean;

import com.lab3.db.ResultDAO;
import com.lab3.entity.ResultEntity;
import com.lab3.mbean.MissRatioMBean;
import com.lab3.mbean.PointCounterMBean;
import com.lab3.model.AreaHitChecker;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Singleton
public class ResultsControllerBean implements Serializable {

    @EJB
    private transient ResultDAO resultDAO;

    @EJB
    private transient PointCounterMBean pointCounter;

    @EJB
    private transient MissRatioMBean missRatio;

    private ArrayList<ResultEntity> results = new ArrayList<>();

    @PostConstruct
    public void init() {
        var resultsEntities = resultDAO.getAllResults();
        results = new ArrayList<>(resultsEntities);
    }

    public void addResult(BigDecimal x, BigDecimal y, BigDecimal r, boolean hit) {

        ResultEntity entity = ResultEntity.builder().x(x).y(y).r(r).hit(hit).build();
        
        results.add(entity);

        resultDAO.addNewResult(entity);

        // Уведомляем MBeans
        if (pointCounter != null) {
            pointCounter.addPoint(hit);
            pointCounter.checkAndNotifyOutOfBounds(x, y, r);
        }
        if (missRatio != null) {
            missRatio.addClick(hit);
        }
    }

    public void addResult(BigDecimal x, BigDecimal y, BigDecimal r) {
        boolean hit = AreaHitChecker.checkHit(x, y, r);

        ResultEntity entity = ResultEntity.builder().x(x).y(y).r(r).hit(hit).build();
        
        results.add(entity);

        resultDAO.addNewResult(entity);

        // Уведомляем MBeans
        if (pointCounter != null) {
            pointCounter.addPoint(hit);
            pointCounter.checkAndNotifyOutOfBounds(x, y, r);
        }
        if (missRatio != null) {
            missRatio.addClick(hit);
        }
    }

    public String getJsonPoints() {
        List<ResultEntity> results = getResults();

        if (results == null || results.isEmpty()) {
            return "[]";
        }

        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < results.size(); i++) {
            ResultEntity result = results.get(i);
            json.append("{");
            json.append("\"x\": ").append(result.getX());
            json.append(",\"y\": ").append(result.getY());
            json.append(",\"r\": ").append(result.getR());
            json.append(",\"hit\": ").append(result.getHit());
            json.append("}");
            if (i < results.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

}

