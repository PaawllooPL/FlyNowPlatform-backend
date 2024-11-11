package com.flynow.service.mappers;

import com.flynow.domain.models.Comment;
import com.flynow.repository.entities.CommentEntity;
import com.flynow.repository.entities.UserEntity;

public class CommentMapper {
    public static Comment toDomain(CommentEntity commentEntity) {
        return Comment.builder()
                .rating(commentEntity.getRating())
                .content(commentEntity.getContent())
                .build();
    }
    public static CommentEntity toEntityWithExistingUser(Comment comment, UserEntity userEntity) {
        return CommentEntity.of(null, comment.getRating(), comment.getContent(), userEntity);
    }
}
