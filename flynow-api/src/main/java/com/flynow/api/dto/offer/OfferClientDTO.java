package com.flynow.api.dto.offer;

import com.flynow.domain.models.offer.OfferClient;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OfferClientDTO {

    private String email;
    private String username;

    public static OfferClientDTO fromDomain (OfferClient offerClient) {
        return OfferClientDTO.builder()
                .email(offerClient.getEmail())
                .username(offerClient.getUsername())
                .build();
    }
}
