package com.flynow.service.models;

import com.flynow.domain.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record UserDetailsImpl(User user) implements UserDetails {

    public Integer getId() {return user.getId();}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user
                .getRoles()
                .stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    /**
     * Spring Security's {@code UserDetails.getUsername()} is the principal
     * identifier used during authentication. This codebase uses email as the
     * login handle, so the JWT subject and {@code loadUserByUsername} lookup
     * are email-based. Use {@link #getActualUsername()} for the display name.
     */
    @Override
    public String getUsername() { return user.getEmail(); }

    public String getActualUsername() { return user.getUsername(); }
}
