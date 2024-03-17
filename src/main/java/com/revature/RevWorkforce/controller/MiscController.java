package com.revature.RevWorkforce.controller;

import com.revature.RevWorkforce.entity.*;
import com.revature.RevWorkforce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MiscController {
    @Autowired
    MiscService miscService;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/holidays")
    public ResponseEntity<List<Holiday>> getHolidays(){
        return ResponseEntity.ok(miscService.getAllHolidays());
    }

    @GetMapping("/notifications/{account_id}")
    public List<Notification> getNotificationsByAccountId(@PathVariable("account_id") Integer account_id) {
        return this.notificationService.getNotificationsByAccountId(account_id);
    }
}
