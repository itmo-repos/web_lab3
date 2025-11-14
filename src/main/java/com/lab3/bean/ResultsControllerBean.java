package com.lab3.bean;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lab3.db.DAOFactory;
import com.lab3.entity.ResultEntity;
import com.lab3.model.AreaHitChecker;

@Data
public class ResultsControllerBean implements Serializable {

    private ArrayList<ResultEntity> results = new ArrayList<>();

    @PostConstruct
    public void init() {
        var resultsEntities = DAOFactory.getInstance().getResultDAO().getAllResults();
        results = new ArrayList<>(resultsEntities);
    }

    public void addResult(BigDecimal x, BigDecimal y, BigDecimal r, boolean hit) {
        ResultEntity entity = ResultEntity.builder().x(x).y(y).r(r).hit(hit).build();
        
        results.add(entity);

        DAOFactory.getInstance().getResultDAO().addNewResult(entity);
    }

    public void addResult(BigDecimal x, BigDecimal y, BigDecimal r) {
        boolean hit = AreaHitChecker.checkHit(x, y, r);

        ResultEntity entity = ResultEntity.builder().x(x).y(y).r(r).hit(hit).build();
        
        results.add(entity);

        DAOFactory.getInstance().getResultDAO().addNewResult(entity);
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
            json.append(",\"hit\": ").append(result.isHit());
            json.append("}");
            if (i < results.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

}

