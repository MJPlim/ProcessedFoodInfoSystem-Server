package com.plim.plimserver.domain.user.dto;

import com.plim.plimserver.domain.user.domain.RoleType;
import com.plim.plimserver.domain.user.domain.StateType;
import com.plim.plimserver.domain.user.domain.User;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ToString
@Getter
public class SignUpUserRequest {

    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    @NotNull(message = "이름을 입력해주세요.")
    private String name;

    private LocalDate birth;

    private String address;

    private String profileImageAddress;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .birth(this.birth)
                .address(this.address)
                .profileImageAddress(this.profileImageAddress)
                .role(RoleType.ROLE_USER)
                .state(StateType.NORMAL)
                .build();
    }

}
