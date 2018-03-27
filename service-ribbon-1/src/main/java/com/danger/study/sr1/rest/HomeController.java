package com.danger.study.sr1.rest;

import com.danger.study.sr1.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private HomeService homeService;

    @RequestMapping("/time")
    public String getTime() {
        return homeService.getTime();
    }
}
