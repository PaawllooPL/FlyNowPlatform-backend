package com.flynow.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Entity
@Table(name = "user_flight")
public class UserFlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "flight_id")
    private FlightEntity flight;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private Boolean didComment;
}
