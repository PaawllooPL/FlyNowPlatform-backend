package com.flynow.api.dto.offer;

import com.flynow.service.models.query.offer.OfferClient;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OfferClientDTO {

    private String email;
    private String username;

    public static OfferClientDTO from(OfferClient offerClient) {
        return new OfferClientDTO(
                offerClient.getEmail(),
                offerClient.getUsername());
    }
}
