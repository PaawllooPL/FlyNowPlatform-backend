package com.flynow.infrastructure.repository.command;

import com.flynow.domain.models.Comment;
import com.flynow.infrastructure.entities.CommentEntity;
import com.flynow.infrastructure.repository.jpa.CommentJpaRepository;
import com.flynow.infrastructure.repository.jpa.CompanyJpaRepository;
import com.flynow.infrastructure.repository.jpa.UserJpaRepository;
import com.flynow.service.repository.command.CommentCommandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentCommandRepositoryImpl implements CommentCommandRepository {

    private final CommentJpaRepository commentJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(Comment comment) {
        var companyProxy = companyJpaRepository.getReferenceById(comment.getCompanyId());
        var userProxy = userJpaRepository.getReferenceById(comment.getUserId());
        commentJpaRepository.save(CommentEntity.of(
                null,
                comment.getRating(),
                comment.getContent(),
                userProxy,
                companyProxy));
    }
}
