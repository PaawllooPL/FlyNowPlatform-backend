package com.flynow.api.controllers;


import com.flynow.api.dto.comment.AddCommentDTO;
import com.flynow.domain.interfaces.usecases.CommentUseCases;
import com.flynow.domain.models.Comment;
import com.flynow.service.mappers.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.flynow.api.ApiPathSegments.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH + COMMENTS)
public class CommentController {

    private final CommentUseCases commentUseCases;

    @PostMapping(COMMENTS_ADD)
    public ResponseEntity<Comment> addComment(@RequestBody AddCommentDTO addCommentDTO) {
        Comment comment = Comment.of(null, null, addCommentDTO.getRating(), addCommentDTO.getContent());
        Comment responseComment = commentUseCases.addComment(comment, addCommentDTO.getCompanyId(), addCommentDTO.getFlightId());

        return ResponseEntity.ok(responseComment);
    }
}
