package com.ufcg.university.entities;

import java.util.List;
import java.util.Map;

public class Metric {
    
    private String name;

    private String metricType;

    private String description;

    private List<Map<String, String>> samples;

    public Metric(String name, String metricType, String description, List<Map<String, String>> samples) {

        this.name = name;
        this.metricType = metricType;
        this.description = description;
        this.samples = samples;

    }

}