package com.flynow.repository.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Entity
@Table(name = "companies")
@Builder
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String tin;

    @Column(nullable = false)
    private String address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private UserEntity organizerAccount;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private List<CommentEntity> comments;
}
