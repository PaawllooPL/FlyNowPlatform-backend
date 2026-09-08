package com.flynow.service.repository.command;

import com.flynow.domain.models.Comment;

public interface CommentCommandRepository {

    void save(Comment comment);
}
