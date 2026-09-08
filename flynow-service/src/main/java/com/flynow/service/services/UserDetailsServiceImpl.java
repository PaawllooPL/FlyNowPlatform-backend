package com.flynow.service.services;

import com.flynow.service.exceptions.user.UserNotFoundException;
import com.flynow.service.models.UserDetailsImpl;
import com.flynow.service.repository.query.UserQueryRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserQueryRepository userQueryRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        return userQueryRepository
                .findByEmail(email)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
