package com.quantum.shipment_service;
import lombok.Data;
@Data
public class ShipmentRequest {
    private String origin;
    private String destination;
    private String customerId;
}
