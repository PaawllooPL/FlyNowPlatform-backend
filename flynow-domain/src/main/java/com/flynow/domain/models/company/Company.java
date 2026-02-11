package com.flynow.domain.models.company;

import com.flynow.domain.models.Comment;
import com.flynow.domain.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
@Builder
public class Company {
    private User organizer;
    private String name;
    private String tin;    //TIN - english version of polish NIP
    private String address;
    private List<Comment> comments;
}
