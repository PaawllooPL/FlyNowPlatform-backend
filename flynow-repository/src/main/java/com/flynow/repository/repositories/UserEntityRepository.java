package com.flynow.repository.repositories;

import com.flynow.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

}