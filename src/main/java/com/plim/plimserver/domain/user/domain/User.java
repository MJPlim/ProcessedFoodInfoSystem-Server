package com.plim.plimserver.domain.user.domain;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.plim.plimserver.domain.user.dto.UserInfoModifyRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.plim.plimserver.domain.review.domain.Review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private UserRoleType role;

    @Column(name = "user_birth")
    private LocalDate birth;

    @Column(name = "user_address")
    private String address;

    @CreationTimestamp
    @Column(name = "user_created_date")
    private Timestamp createdDate;

    @UpdateTimestamp
    @Column(name = "user_modified_date")
    private Timestamp updatedDate;

    @Column(name = "user_image_address")
    private String profileImageAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_state", nullable = false)
    private UserStateType state;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_provider", nullable = false)
    private UserProvider provider;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public User(String email, String password, String name, UserRoleType role, LocalDate birth,
                String address, String profileImageAddress, UserStateType state, UserProvider provider) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.birth = birth;
        this.address = address;
        this.profileImageAddress = profileImageAddress;
        this.state = state;
        this.provider = provider;
    }

    public void withdraw() {
        this.state = UserStateType.DELETED;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void emailVerificationCompleted() {
        this.state = UserStateType.NORMAL;
    }

    public void modifyUserInfo(UserInfoModifyRequest request) {
        this.name = request.getName();
        this.birth = request.getBirth();
        this.address = request.getAddress();
    }
}
