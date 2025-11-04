package com.quantum.shipment_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {
    @Autowired
    private KafkaProducerService kafkaProducerService;
    @PostMapping
    public ResponseEntity<String> createShipment(@RequestBody ShipmentRequest shipment) {
        kafkaProducerService.sendShipment(shipment);
        return ResponseEntity.ok("Shipment creation request received for: " + shipment.getCustomerId());
    }
}
