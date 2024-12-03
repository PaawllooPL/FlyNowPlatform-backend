package com.flynow.api.controllers;


import com.flynow.api.dto.comment.AddCommentDTO;
import com.flynow.domain.interfaces.usecases.CommentUseCases;
import com.flynow.domain.models.Comment;
import com.flynow.service.mappers.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.flynow.api.ApiPathSegments.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH + COMMENTS)
public class CommentController {

    private final CommentUseCases commentUseCases;

    @PostMapping(value = COMMENTS_ADD, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addComment(@RequestBody AddCommentDTO addCommentDTO) {
        Comment comment = Comment.of(null, null, addCommentDTO.getRating(), addCommentDTO.getContent());
        Comment responseComment = commentUseCases.addComment(comment, addCommentDTO.getCompanyId(), addCommentDTO.getFlightId());

        return ResponseEntity.status(HttpStatus.OK).body("Comment created successfully.");
    }
}
