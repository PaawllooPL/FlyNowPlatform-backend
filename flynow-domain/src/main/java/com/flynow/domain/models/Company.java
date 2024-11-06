package com.flynow.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
@Builder
public class Company {
    private String name;
    private String tin;    //TIN - english version of polish NIP
    private String address;
    private List<Comment> comments;
}
