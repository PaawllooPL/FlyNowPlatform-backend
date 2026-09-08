package com.flynow.service.commands;


public record AddCommentCommand(Integer flightId, Integer rating, String content) {

}
