package com.flynow.api.dto.comment;

import lombok.Getter;

@Getter
public class AddCommentDTO {
    private Integer flightId;
    private String content;
    private Integer rating;
}
