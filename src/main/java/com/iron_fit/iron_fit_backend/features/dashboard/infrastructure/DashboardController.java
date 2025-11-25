package com.iron_fit.iron_fit_backend.features.dashboard.infrastructure;

import com.iron_fit.iron_fit_backend.features.dashboard.application.services.GetDashboardStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/dashboard")
public class DashboardController {

    @Autowired
    private GetDashboardStats getDashboardStats;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        HashMap<String, Object> result = getDashboardStats.execute();
        if (result.containsKey("error"))
            return ResponseEntity.badRequest().body(result);
        else
            return ResponseEntity.ok().body(result);
    }
}
