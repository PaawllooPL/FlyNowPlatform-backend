package com.flynow.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class AircraftType {
    private Integer id;
    private String name;
}
