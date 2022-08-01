package com.ufcg.university.integration;

import org.api.mocktests.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class StudentTests {

    @Autowired
    private MockMvc mockMvc;

    private final RequestUtils requestUtils = new RequestUtils(this);


}
