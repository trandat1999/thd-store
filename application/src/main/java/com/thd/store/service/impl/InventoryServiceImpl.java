package com.thd.store.service.impl;

import com.thd.store.entity.Inventory;
import com.thd.store.entity.InvoiceImport;
import com.thd.store.repository.InventoryRepository;
import com.thd.store.service.InventoryService;
import com.thd.store.type.InventoryStatusType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DatNuclear 01/05/2024
 * @project store
 */
@Service
@AllArgsConstructor
public class InventoryServiceImpl extends BaseService implements InventoryService {
    private final InventoryRepository inventoryRepository;
    @Override
    public void warehoused(InvoiceImport invoiceImport) {
        List<Inventory> inventories = new ArrayList<>();
        for(var item  : invoiceImport.getItems()){
            Inventory inventory = Inventory.builder()
                    .status(InventoryStatusType.IN_BOUND.getStatus())
                    .product(item.getProduct())
                    .warehouse(invoiceImport.getWarehouse())
                    .quantity(item.getQuantity())
                    .invoiceImport(invoiceImport)
                    .build();
            inventories.add(inventory);
        }
        inventoryRepository.saveAll(inventories);
    }
}
