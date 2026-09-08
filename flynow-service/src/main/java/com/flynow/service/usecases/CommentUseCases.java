package com.flynow.service.usecases;

import com.flynow.domain.models.Comment;
import com.flynow.service.commands.AddCommentCommand;

public interface CommentUseCases {
    void addComment(AddCommentCommand commentCommand);
    Comment addComment (Integer companyId, Integer userId, Comment comment); //test
    boolean canComment (Integer flightId);
}
