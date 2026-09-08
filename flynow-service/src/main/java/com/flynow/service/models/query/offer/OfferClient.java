package com.flynow.service.models.query.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(staticName = "of")
public class OfferClient {

    private String email;
    private String username;
}
