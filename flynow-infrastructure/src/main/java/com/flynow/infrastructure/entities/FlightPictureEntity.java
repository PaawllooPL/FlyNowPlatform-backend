package com.flynow.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Entity
@Table(name = "flight_picture")
public class FlightPictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FlightEntity flight;
    /**
     * File name with extension e.g. {@code photo_1.jpg}
     */
    @Column(nullable = false)
    private String pictureFileName;
}
