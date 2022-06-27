package com.ufcg.university.controllers;

import io.prometheus.client.Collector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Collector.MetricFamilySamples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.MetricResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.university.entities.Metric;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @Autowired
    private CollectorRegistry collectorRegistry;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/detailed")
    public ResponseEntity<?> getDetailedMetrics() {

        List<Metric> samplesList = new ArrayList<>();
        
        this.collectorRegistry.metricFamilySamples().asIterator().forEachRemaining(s -> samplesList.add(new Metric(s)));

        return new ResponseEntity<>(samplesList, HttpStatus.OK);
    }


    @GetMapping("/generic")
    public ResponseEntity<?> getGenericMetrics() {
        
        MetricsEndpoint metricsEndpoint = (MetricsEndpoint) applicationContext.getBean("metricsEndpoint", MetricsEndpoint.class);

        List<MetricResponse> metrics = new ArrayList<>();

        metricsEndpoint.listNames().getNames().iterator().forEachRemaining(name -> metrics.add(metricsEndpoint.metric(name, null)));
        
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }

    

}
