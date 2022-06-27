package com.ufcg.university.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.springframework.boot.jackson.JsonComponent;

import io.prometheus.client.Collector;
import io.prometheus.client.Collector.MetricFamilySamples.Sample;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Metric {
    
    private String name;

    private String metricType;

    private String baseUnit;

    private String description;

    private List<Map<String, Object>> samples;

    public Metric(String name, String metricType, String description, List<Map<String, Object>> samples) {

        this.name = name;
        this.metricType = metricType;
        this.description = description;
        this.samples = samples;
    }

    public Metric(Collector.MetricFamilySamples metric) {

        this.name = metric.name;
        this.description = metric.help;
        this.metricType = metric.type.toString();
        this.baseUnit = metric.unit;
        this.samples = new ArrayList<>();
        
        for (Sample sample : metric.samples) {
            Map<String, Object> s = new HashMap<>();
            s.put("name", sample.name);
            for (int i = 0; i < sample.labelNames.size(); i++) { s.put(sample.labelNames.get(i), sample.labelValues.get(i)); }
            s.put("value", sample.value);
            s.put("timestampsMs", sample.timestampMs);
            s.put("exemplar", sample.exemplar);
            samples.add(s);
        }

    }

}