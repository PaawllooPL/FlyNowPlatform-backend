package com.flynow.service.services;

import com.flynow.domain.interfaces.usecases.CommentUseCases;
import com.flynow.domain.models.Comment;
import com.flynow.repository.entities.*;
import com.flynow.repository.repositories.jpa.*;
import com.flynow.service.exceptions.CompanyNotFoundException;
import com.flynow.service.exceptions.flight.FlightNotFoundException;
import com.flynow.service.exceptions.UserNotFoundException;
import com.flynow.service.exceptions.comment.CommentNotAllowedException;
import com.flynow.service.mappers.CommentMapper;
import com.flynow.service.models.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CommentServiceImpl implements CommentUseCases {

    private final UserJpaRepository userJpaRepository;
    private final CompanyJpaRepository companyJpaRepository;
    private final FlightJpaRepository flightJpaRepository;
    private final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Override
    @Transactional
    public Comment addComment(Comment comment, Integer flightId) throws RuntimeException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        FlightEntity flight = flightJpaRepository.findById(flightId).orElseThrow(FlightNotFoundException::new); //finding flight

        canComment(flightId); //check if user can comment

        flight.getCompany().getComments().add(CommentMapper.toEntityWithExistingUser(comment, userDetails.getUserEntity()));    //adding comment
        flight.getJunctionClients().stream().filter(jc -> jc.getUser().getId() == userDetails.getUserEntity().getId()).findFirst()
                .ifPresentOrElse(
                        jc -> jc.setDidComment(true),
                        () -> {throw new CommentNotAllowedException("User not on flight list");}
                );
        flightJpaRepository.save(flight);
        return Comment.of(userDetails.getUserEntity().getId(), userDetails.getUserEntity().getUsername(),
                comment.getRating(), comment.getContent());
    }

    @Override
    @Transactional
    public Comment addComment(Integer companyId, Integer userId, Comment comment) {
        logger.error("Comment content: {}", comment.getContent());
        logger.error("Comment rating: {}", comment.getRating());
        CompanyEntity companyEntity = companyJpaRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(String.format("Company with id: %d not found", companyId)));

        UserEntity userEntity = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("user with id: %d not found", userId)));
        CommentEntity commentEntity = CommentMapper.toEntityWithExistingUser(comment, userEntity);
        logger.error("Comment entity content: {}", commentEntity.getContent());
        logger.error("Comment entity rating: {}", commentEntity.getRating());
        logger.error("Comment entity comment creator email: {}", commentEntity.getCommentCreator().getEmail());
        companyEntity.getComments().add(commentEntity);

        companyJpaRepository.save(companyEntity);
        return comment;
    }
    @Override
    public boolean canComment(Integer flightId) throws RuntimeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return false;

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserEntity user = userDetails.getUserEntity();
        var flight = flightJpaRepository.findById(flightId).orElseThrow(
                () -> new FlightNotFoundException(String.format("Flight with id: %d not found", flightId)));

        if(LocalDateTime.now().isBefore(flight.getFlightDate()))
            return false;
        if(LocalDateTime.now().isAfter(flight.getFlightDate().plusWeeks(2)))
            return false;
        if(flight.getJunctionClients().stream().noneMatch(jc -> jc.getUser().getId() == user.getId()))
            return false;
        if(flight.getJunctionClients().stream().filter(jc -> jc.getUser().getId() == user.getId()).findFirst().get().getDidComment())
            return false;

        return true;
    }
}
