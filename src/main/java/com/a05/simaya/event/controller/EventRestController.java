package com.a05.simaya.event.controller;

import com.a05.simaya.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EventRestController {
    @Autowired
    private EventService eventService;

    @GetMapping("/chart-data")
    public Map<String, Integer> getChartData() {
        Integer countDone = eventService.countDone();
        Integer countNotDone = eventService.countNotDone();
        Map<String, Integer> data = new HashMap<>();
        data.put("done", countDone);
        data.put("notDone", countNotDone);
        return data;
    }
}
