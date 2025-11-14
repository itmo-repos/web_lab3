package com.lab3.bean;

import java.io.Serializable;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import jakarta.ejb.Stateless;

@Stateless
public class TimeBean {
    private final DateTimeFormatter isoFormatter;

    public TimeBean() {
        isoFormatter = DateTimeFormatter.ISO_INSTANT;
    }


    public String getCurrentTime() {
        return isoFormatter.format(Instant.now());
    }
}