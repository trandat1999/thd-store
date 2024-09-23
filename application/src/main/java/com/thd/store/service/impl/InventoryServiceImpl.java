package com.thd.store.service.impl;

import com.thd.store.dto.BaseResponse;
import com.thd.store.entity.Inventory;
import com.thd.store.entity.InvoiceImport;
import com.thd.store.entity.SaleInvoice;
import com.thd.store.repository.InventoryRepository;
import com.thd.store.service.InventoryService;
import com.thd.store.type.InventoryStatusType;
import com.thd.store.util.SystemMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.thd.store.util.ConstUtil.KAFKA_GROUP_ORDER;
import static com.thd.store.util.ConstUtil.KAFKA_TOPIC_UPDATE_WAREHOUSE;

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

    @Override
    @KafkaListener(topics = KAFKA_TOPIC_UPDATE_WAREHOUSE, groupId = KAFKA_GROUP_ORDER)
    public void outStock(SaleInvoice saleInvoice) {
        List<Inventory> inventories = new ArrayList<>();
        for(var item  : saleInvoice.getItems()){
            Inventory inventory = Inventory.builder()
                    .status(InventoryStatusType.OUT_BOUND.getStatus())
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .saleInvoice(saleInvoice)
                    .build();
            inventories.add(inventory);
        }
        inventoryRepository.saveAll(inventories);
    }
}
