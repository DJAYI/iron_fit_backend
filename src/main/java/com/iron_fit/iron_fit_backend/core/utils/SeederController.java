package com.iron_fit.iron_fit_backend.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/seeder")
@Slf4j
public class SeederController {

    @Autowired
    private DatabaseSeeder databaseSeeder;

    /**
     * Seeds the database with fake data
     * 
     * @param quantity Number of records to create per entity (default: 1000)
     * @return Response with seeding statistics
     * 
     *         Example usage:
     *         POST /api/seeder/seed?quantity=1000
     *         POST /api/seeder/seed (uses default 1000)
     */
    @PreAuthorize("hasRole('AUDITOR')")
    @PostMapping("/seed")
    public ResponseEntity<?> seedDatabase(@RequestParam(defaultValue = "1000") int quantity) {
        log.info("Received request to seed database with {} records per entity", quantity);

        if (quantity <= 0) {
            return ResponseEntity.badRequest().body(new HashMap<String, Object>() {
                {
                    put("message", "Quantity must be greater than 0");
                    put("error", true);
                }
            });
        }

        if (quantity > 10000) {
            return ResponseEntity.badRequest().body(new HashMap<String, Object>() {
                {
                    put("message", "Quantity cannot exceed 10000 records per entity for performance reasons");
                    put("error", true);
                }
            });
        }

        HashMap<String, Object> result = databaseSeeder.seedDatabase(quantity);

        if (result.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Quick seed with common quantities
     */
    @PreAuthorize("hasRole('AUDITOR')")
    @PostMapping("/seed/small")
    public ResponseEntity<?> seedSmall() {
        return seedDatabase(100);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PostMapping("/seed/medium")
    public ResponseEntity<?> seedMedium() {
        return seedDatabase(500);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PostMapping("/seed/large")
    public ResponseEntity<?> seedLarge() {
        return seedDatabase(1000);
    }

    @PreAuthorize("hasRole('AUDITOR')")
    @PostMapping("/seed/xlarge")
    public ResponseEntity<?> seedXLarge() {
        return seedDatabase(5000);
    }
}
