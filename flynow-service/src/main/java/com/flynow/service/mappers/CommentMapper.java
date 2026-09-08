package com.flynow.service.mappers;

// COMMENTED OUT — moved to flynow-infrastructure (depends on infrastructure entities, was placed in service by mistake).
// Kept here as a placeholder so the package and historical code are not lost.
/*
import com.flynow.domain.models.Comment;
import com.flynow.infrastructure.entities.CommentEntity;
import com.flynow.infrastructure.entities.UserEntity;

public class CommentMapper {
    public static Comment toDomain(CommentEntity commentEntity) {
        return Comment.builder()
                .userId(commentEntity.getCommentCreator().getId())
                .username(commentEntity.getCommentCreator().getUsername())
                .rating(commentEntity.getRating())
                .content(commentEntity.getContent())
                .build();
    }
    public static CommentEntity toEntityWithExistingUser(Comment comment, UserEntity userEntity) {
        return CommentEntity.of(null, comment.getRating(), comment.getContent(), userEntity);
    }
}
*/
