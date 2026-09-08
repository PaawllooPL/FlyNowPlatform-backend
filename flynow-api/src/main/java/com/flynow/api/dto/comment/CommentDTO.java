package com.flynow.api.dto.comment;

import com.flynow.service.models.query.offer.OfferComment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDTO {

    private Integer id;
    private Integer userId;
    private String username;
    private Integer rating;
    private String content;

    public static CommentDTO from(OfferComment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getUserId(),
                comment.getUsername(),
                comment.getRating(),
                comment.getContent());
    }
}
