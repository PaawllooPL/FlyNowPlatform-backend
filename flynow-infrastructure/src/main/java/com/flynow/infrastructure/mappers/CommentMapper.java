package com.flynow.infrastructure.mappers;

import com.flynow.domain.models.Comment;
import com.flynow.infrastructure.entities.CommentEntity;
import com.flynow.infrastructure.entities.CompanyEntity;
import com.flynow.infrastructure.entities.UserEntity;

public class CommentMapper {
    public static Comment toDomain(CommentEntity commentEntity) {
        // Comment field order: id, userId, companyId, username, rating, content
        // CommentEntity does not carry companyId directly; the Company lives in a separate FK.
        return Comment.of(
                commentEntity.getId(),
                commentEntity.getCommentCreator().getId(),
                null,
                commentEntity.getCommentCreator().getUsername(),
                commentEntity.getRating(),
                commentEntity.getContent());
    }
    public static CommentEntity toEntityWithExistingUser(Comment comment, UserEntity userEntity, CompanyEntity companyEntity) {
        // CommentEntity field order: id, rating, content, commentCreator, company
        return CommentEntity.of(null, comment.getRating(), comment.getContent(), userEntity, companyEntity);
    }
}
