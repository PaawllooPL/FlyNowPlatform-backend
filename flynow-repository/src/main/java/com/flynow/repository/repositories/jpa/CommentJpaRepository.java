package com.flynow.repository.repositories.jpa;

import com.flynow.repository.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Integer> {
}
