package com.flynow.domain.interfaces.usecases;

import com.flynow.domain.models.Comment;
import com.flynow.domain.models.Company;

public interface CommentUseCases {
    public Comment AddComment(Comment comment, Integer CompanyId);
}
