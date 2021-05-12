package com.plim.plimserver.domain.user.domain;


import com.plim.plimserver.domain.allergy.domain.UserAllergy;
import com.plim.plimserver.domain.review.domain.Review;
import com.plim.plimserver.domain.user.dto.UserInfoModifyRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_second_email", unique = true)
    private String secondEmail;

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
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAllergy> userAllergyList = new ArrayList<>();
    
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

    public User modifyUserInfo(UserInfoModifyRequest request) {
        this.name = request.getName();
        this.birth = request.getBirth();
        this.address = request.getAddress();
        return this;
    }
}
