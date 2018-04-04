package com.danger.study.sf1.service.impl;

import com.danger.study.sf1.service.HomeService;
import org.springframework.stereotype.Component;

@Component
public class HomeServiceImpl implements HomeService {

    @Override
    public String getTime() {
        return "Sorry, Error!";
    }
}
