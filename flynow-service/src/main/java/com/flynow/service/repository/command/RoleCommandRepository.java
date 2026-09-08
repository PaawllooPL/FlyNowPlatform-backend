package com.flynow.service.repository.command;

import com.flynow.domain.models.Role;

public interface RoleCommandRepository {
    void addRoleForUserById(Integer id, Role role);
}
