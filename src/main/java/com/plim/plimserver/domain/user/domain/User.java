package com.plim.plimserver.domain.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private RoleType role;

    @Column(name = "user_birth")
    private LocalDate birth;

    @Column(name = "user_address")
    private String address;

    @CreationTimestamp
    @Column(name = "user_register_date")
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "user_created_date")
    private Timestamp updatedDate;

    @Column(name = "user_image_address")
    private String profileImageAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_state", nullable = false)
    private StateType state;

    @Builder
    public User(String email, String password, String name, RoleType role,
                LocalDate birth, String address, String profileImageAddress, StateType state) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.birth = birth;
        this.address = address;
        this.profileImageAddress = profileImageAddress;
        this.state = state;
    }

    public void withdraw() {
        this.state = StateType.DELETED;
    }

}
