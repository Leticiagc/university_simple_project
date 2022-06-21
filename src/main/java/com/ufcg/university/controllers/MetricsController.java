package com.ufcg.university.controllers;

import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @Autowired
    private CollectorRegistry collectorRegistry;

    @GetMapping
    public ResponseEntity<?> myMetrics() {

        Enumeration<Collector.MetricFamilySamples> samples = this.collectorRegistry.metricFamilySamples();

        List<List<Collector.MetricFamilySamples.Sample>> samplesList = new ArrayList<>();

        while (samples.hasMoreElements()) {
            samplesList.add(samples.nextElement().samples);
        }

        return new ResponseEntity<>(samplesList, HttpStatus.OK);
    }

}
