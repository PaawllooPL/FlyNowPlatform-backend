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
@Table(name = "users")
public class UserEntity {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> accountRoles;
}
