package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.entities.InventoryLog;

import java.util.List;
import java.util.Optional;

public interface InventoryLogService {
    List<InventoryLog> getAllInventoryLogs();
    Optional<InventoryLog> getInventoryLogById(Long id);
    InventoryLog createInventoryLog(InventoryLog inventoryLog);
    Optional<InventoryLog> updateInventoryLog(Long id, InventoryLog inventoryLog);
    boolean deleteInventoryLog(Long id);
}
