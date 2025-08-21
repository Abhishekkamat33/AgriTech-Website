package com.adhunikkethi.adhunnikkethi.controllers;

import com.adhunikkethi.adhunnikkethi.Services.InventoryLogService;
import com.adhunikkethi.adhunnikkethi.entities.InventoryLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-logs")
public class InventoryLogController {

    private final InventoryLogService inventoryLogService;

    public InventoryLogController(InventoryLogService inventoryLogService) {
        this.inventoryLogService = inventoryLogService;
    }

    @GetMapping
    public List<InventoryLog> getAllInventoryLogs() {
        return inventoryLogService.getAllInventoryLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryLog> getInventoryLogById(@PathVariable Long id) {
        return inventoryLogService.getInventoryLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InventoryLog> createInventoryLog(@RequestBody InventoryLog inventoryLog) {
        InventoryLog createdLog = inventoryLogService.createInventoryLog(inventoryLog);
        return new ResponseEntity<>(createdLog, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryLog> updateInventoryLog(@PathVariable Long id, @RequestBody InventoryLog inventoryLogDetails) {
        return inventoryLogService.updateInventoryLog(id, inventoryLogDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryLog(@PathVariable Long id) {
        boolean deleted = inventoryLogService.deleteInventoryLog(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
