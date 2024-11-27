package com.flynow.service.exceptions.comment;


public class CommentNotAllowedException extends RuntimeException {
    public CommentNotAllowedException() {}
    public CommentNotAllowedException(String message) {super(message);}

}
