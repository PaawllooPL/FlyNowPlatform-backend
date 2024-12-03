package com.flynow.service.services;

import com.flynow.domain.interfaces.usecases.UserUseCases;
import com.flynow.repository.repositories.jpa.CompanyJpaRepository;
import com.flynow.repository.repositories.jpa.FlightJpaRepository;
import com.flynow.repository.repositories.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class UserServiceImpl implements UserUseCases {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final CompanyJpaRepository companyJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final FlightJpaRepository flightJpaRepository;
}
