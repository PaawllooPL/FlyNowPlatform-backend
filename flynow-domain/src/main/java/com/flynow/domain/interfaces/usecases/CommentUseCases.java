package com.flynow.domain.interfaces.usecases;

import com.flynow.domain.models.Comment;

public interface CommentUseCases {
    Comment addComment(Comment comment, Integer flightId);
    Comment addComment (Integer companyId, Integer userId, Comment comment); //test
    boolean canComment (Integer flightId);
}
