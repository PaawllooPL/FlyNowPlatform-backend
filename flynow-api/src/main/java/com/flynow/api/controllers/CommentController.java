package com.flynow.api.controllers;


import com.flynow.api.dto.comment.AddCommentDTO;
import com.flynow.service.commands.AddCommentCommand;
import com.flynow.service.usecases.CommentUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.flynow.api.ApiPathSegments.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_PATH + COMMENTS)
public class CommentController {

    private final CommentUseCases commentUseCases;

    @PostMapping(value = COMMENTS_ADD, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addComment(@RequestBody AddCommentDTO addCommentDTO) {
        var addCommentCommand = new AddCommentCommand(addCommentDTO.getFlightId(), addCommentDTO.getRating(), addCommentDTO.getContent());
        commentUseCases.addComment(addCommentCommand);

        return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully.");
    }
}
