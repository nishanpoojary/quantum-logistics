package com.quantum.analytics_service;

import lombok.Data;

@Data
public class ShipmentRequestDTO {
    private String origin;
    private String destination;
    private String customerId;
}
