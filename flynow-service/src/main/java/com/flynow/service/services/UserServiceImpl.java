package com.flynow.service.services;

import com.flynow.domain.interfaces.usecases.UserUseCases;
import com.flynow.domain.models.Comment;
import com.flynow.repository.entities.CommentEntity;
import com.flynow.repository.entities.CompanyEntity;
import com.flynow.repository.entities.UserEntity;
import com.flynow.repository.repositories.jpa.CommentJpaRepository;
import com.flynow.repository.repositories.jpa.CompanyJpaRepository;
import com.flynow.repository.repositories.jpa.FlightJpaRepository;
import com.flynow.repository.repositories.jpa.UserJpaRepository;
import com.flynow.service.exceptions.CompanyNotFoundException;
import com.flynow.service.exceptions.UserNotFoundException;
import com.flynow.service.mappers.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserUseCases {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final CompanyJpaRepository companyJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final FlightJpaRepository flightJpaRepository;


    @Override
    public void buyFlightSeat(Integer flightId) {

    }


}
