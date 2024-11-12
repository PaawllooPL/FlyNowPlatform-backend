package com.flynow.repository.repositories.jpa;

import com.flynow.repository.entities.FlightPictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightPictureJpaRepository extends JpaRepository<FlightPictureEntity, Integer> {

}
