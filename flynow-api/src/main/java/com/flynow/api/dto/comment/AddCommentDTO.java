package com.flynow.api.dto.comment;

import com.flynow.domain.models.Comment;
import lombok.Getter;

@Getter
public class AddCommentDTO {
    private Integer companyId;
    private String content;
    private Integer rating;

    public Comment toDomainComment() {
        return Comment.of(rating, content);
    }
}
