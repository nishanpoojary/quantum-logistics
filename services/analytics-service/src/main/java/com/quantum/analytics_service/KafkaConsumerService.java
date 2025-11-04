package com.quantum.analytics_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    // This method will automatically be called when a message appears on this topic
    @KafkaListener(topics = "shipments-created", groupId = "analytics-group")
    public void handleShipmentCreated(ShipmentRequestDTO shipmentRequest) {
        System.out.println("Received shipment in analytics-service: " + shipmentRequest.getCustomerId());

        // Convert the DTO (Kafka message) into a database Entity
        Shipment shipment = new Shipment(shipmentRequest);
        
        // Save the entity to the Postgres database
        shipmentRepository.save(shipment);
        
        System.out.println("Saved shipment to database with ID: " + shipment.getId());
    }
}
