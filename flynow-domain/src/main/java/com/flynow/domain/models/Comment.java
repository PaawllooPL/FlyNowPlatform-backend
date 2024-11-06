package com.flynow.domain.models;

import lombok.Builder;

@Builder
public class Comment {

    private Integer rating;
    private String content;
}
