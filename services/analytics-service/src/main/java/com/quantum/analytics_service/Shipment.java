package com.quantum.analytics_service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "shipments")
@NoArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origin;
    private String destination;
    private String customerId;

    // Constructor to convert from our DTO
    public Shipment(ShipmentRequestDTO dto) {
        this.origin = dto.getOrigin();
        this.destination = dto.getDestination();
        this.customerId = dto.getCustomerId();
    }
}
