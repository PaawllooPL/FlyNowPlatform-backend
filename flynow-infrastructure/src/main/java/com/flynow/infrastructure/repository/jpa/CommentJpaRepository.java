package com.flynow.infrastructure.repository.jpa;

import com.flynow.infrastructure.entities.CommentEntity;
import com.flynow.service.models.query.offer.OfferComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommentJpaRepository extends JpaRepository<CommentEntity, Integer> {

    @Query("select new com.flynow.service.models.query.offer.OfferComment( " +
            "c.id," +
            "u.id," +
            "u.username," +
            "c.rating," +
            "c.content) " +
            "from CommentEntity c " +
            "JOIN UserEntity u ON u = c.commentCreator " +
            "WHERE c.company.id = :companyId")
    List<OfferComment> findAllOfferCommentForCompany(@Param("companyId") Integer companyId);
}
