package com.iron_fit.iron_fit_backend.features.people_management.auditors.infrastructure;

import com.iron_fit.iron_fit_backend.features.people_management.auditors.application.services.CreateAuditorService;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.application.services.ListAuditorsService;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.application.services.UpdateAuditorService;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.dto.request.CreateAuditorRequestDto;
import com.iron_fit.iron_fit_backend.features.people_management.auditors.domain.dto.request.UpdateAuditorRequestDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/auditors")
public class AuditorController {
    @Autowired
    private CreateAuditorService createAuditorService;

    @Autowired
    private UpdateAuditorService updateAuditorService;

    @Autowired
    private ListAuditorsService listAuditorsService;

    @PostMapping
    public ResponseEntity<?> createAuditor(@RequestBody @Valid CreateAuditorRequestDto createAuditorRequestDto) {
        HashMap<String, Object> result = createAuditorService.execute(createAuditorRequestDto);

        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuditor(@RequestBody @Valid UpdateAuditorRequestDto updateAuditorRequestDto, @PathVariable Long id) {
        HashMap<String, Object> result = updateAuditorService.execute(updateAuditorRequestDto, id);
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<?> getAuditors() {
        HashMap<String, Object> result = listAuditorsService.execute();
        if (result.containsKey("error")) return ResponseEntity.badRequest().body(result);
        else return ResponseEntity.ok().body(result);
    }
}
