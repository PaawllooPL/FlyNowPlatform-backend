package com.flynow.domain.interfaces.usecases;

import com.flynow.domain.models.Comment;
import com.flynow.domain.models.Company;

public interface CommentUseCases {
    Comment addComment(Comment comment, Integer CompanyId, Integer flightId);
    Comment addComment (Integer companyId, Integer userId, Comment comment);
    boolean canComment (Integer flightId);
}
