package com.zerry.flix_auth.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Role 엔티티
 * 사용자 권한을 저장합니다.
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 255)
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
