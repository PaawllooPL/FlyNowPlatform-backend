package com.flynow.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Comment {

    private Integer rating;
    private String content;
}
