package com.flynow.repository.entities;

import com.flynow.models.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
}
