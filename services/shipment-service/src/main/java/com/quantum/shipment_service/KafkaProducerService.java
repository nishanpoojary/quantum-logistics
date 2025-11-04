package com.quantum.shipment_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
public class KafkaProducerService {
    public static final String TOPIC_NAME = "shipments-created";
    @Autowired
    private KafkaTemplate<String, ShipmentRequest> kafkaTemplate;
    public void sendShipment(ShipmentRequest shipment) {
        kafkaTemplate.send(TOPIC_NAME, shipment.getCustomerId(), shipment);
        System.out.println("Sent shipment to Kafka: " + shipment.getCustomerId());
    }
}
