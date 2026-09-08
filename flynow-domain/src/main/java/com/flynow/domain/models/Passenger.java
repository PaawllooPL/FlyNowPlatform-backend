package com.flynow.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor(staticName = "of")
@Setter
public class Passenger {

    private Integer id;
    private Integer flightId;
    private Integer userId;
    private Boolean didComment;
}
