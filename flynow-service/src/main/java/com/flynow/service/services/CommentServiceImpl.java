package com.flynow.service.services;

import com.flynow.domain.models.Comment;
import com.flynow.domain.models.Flight;
import com.flynow.domain.models.Passenger;
import com.flynow.service.commands.AddCommentCommand;
import com.flynow.service.exceptions.comment.CommentNotAllowedException;
import com.flynow.service.exceptions.flight.FlightNotFoundException;
import com.flynow.service.exceptions.user.NotAuthenticatedException;
import com.flynow.service.models.UserDetailsImpl;
import com.flynow.service.repository.command.CommentCommandRepository;
import com.flynow.service.repository.command.UserFlightCommandRepository;
import com.flynow.service.repository.query.FlightQueryRepository;
import com.flynow.service.repository.query.UserFlightQueryRepository;
import com.flynow.service.usecases.CommentUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CommentServiceImpl implements CommentUseCases {

    private final FlightQueryRepository flightQueryRepository;
    private final UserFlightQueryRepository userFlightQueryRepository;
    private final UserFlightCommandRepository userFlightCommandRepository;
    private final CommentCommandRepository commentCommandRepository;

    @Override
    public void addComment(AddCommentCommand command) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            throw new NotAuthenticatedException("Not authenticated user tried to add comment");
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer userId = userDetails.getId();
        Integer flightId = command.flightId();

        Flight flight = flightQueryRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight with id: " + flightId + " not found"));

        if (!canComment(flightId, userId))
            throw new CommentNotAllowedException("User id: " + userId + " is not allowed to comment on flight id: " + flightId);

        Comment comment = Comment.builder()
                .userId(userId)
                .companyId(flight.getCompanyId())
                .username(userDetails.user().getUsername())
                .rating(command.rating())
                .content(command.content())
                .build();
        commentCommandRepository.save(comment);

        Passenger booking = userFlightQueryRepository.findPassenger(userId, flightId)
                .orElseThrow(() -> new CommentNotAllowedException(
                        "User id: " + userId + " is not on passenger list of flight id: " + flightId));
        booking.setDidComment(true);
        userFlightCommandRepository.save(booking);
    }

    @Override
    public Comment addComment(Integer companyId, Integer userId, Comment comment) {
        // Test path: caller has already constructed the domain Comment with companyId + userId set.
        commentCommandRepository.save(comment);
        return comment;
    }

    @Override
    public boolean canComment(Integer flightId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return false;
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return canComment(flightId, userDetails.getId());
    }

    private boolean canComment(Integer flightId, Integer userId) {
        Flight flight = flightQueryRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException("Flight with id: " + flightId + " not found"));
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(flight.getFlightDate()))
            return false;
        if (now.isAfter(flight.getFlightDate().plusWeeks(2)))
            return false;
        Passenger booking = userFlightQueryRepository.findPassenger(userId, flightId).orElse(null);
        return booking != null && !booking.getDidComment();
    }
}
