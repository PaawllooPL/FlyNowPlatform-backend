package com.flynow.service.models.query.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class OfferComment {
    private Integer id;
    private Integer userId;
    private String username;
    private Integer rating;
    private String content;
}
