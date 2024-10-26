package com.flynow.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Entity
@Table(name = "flight_picture")
public class FlightPictureEntity {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String pathToFile;
}
