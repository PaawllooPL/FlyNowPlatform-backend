package com.flynow.repository.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "aircraft_type")
public class AircraftTypeEntity {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;
}
