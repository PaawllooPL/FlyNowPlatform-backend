package com.flynow.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class CompanyEntity {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String tin;

    @Column(nullable = false)
    private String address;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity organizerAccount;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private List<CommentEntity> comments;
}
