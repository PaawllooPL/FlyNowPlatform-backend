package com.flynow.service.mappers;

import com.flynow.domain.models.Comment;
import com.flynow.repository.entities.CommentEntity;

public class CommentMapper {
    public static Comment toCompany(CommentEntity commentEntity) {
        return Comment.builder()
                .rating(commentEntity.getRating())
                .content(commentEntity.getContent())
                .build();
    }
}
