package com.auca.pet_shop_management_system.controller;

import com.auca.pet_shop_management_system.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> summary() {
        return new ResponseEntity<>(dashboardService.summary(), HttpStatus.OK);
    }
}