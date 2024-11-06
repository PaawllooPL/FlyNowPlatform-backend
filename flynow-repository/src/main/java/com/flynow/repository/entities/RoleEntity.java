package com.flynow.repository.entities;

import com.flynow.domain.models.RoleEnum;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
}
