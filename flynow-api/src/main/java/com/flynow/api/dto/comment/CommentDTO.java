package com.flynow.api.dto.comment;

import com.flynow.domain.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class CommentDTO {

    private Integer userId;
    private String username;
    private Integer rating;
    private String content;


}
