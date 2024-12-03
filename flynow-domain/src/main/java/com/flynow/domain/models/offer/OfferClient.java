package com.flynow.domain.models.offer;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OfferClient {

    private String email;
    private String username;
}
