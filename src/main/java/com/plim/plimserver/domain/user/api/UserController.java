package com.plim.plimserver.domain.user.api;

import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.dto.SignUpUserRequest;
import com.plim.plimserver.domain.user.dto.SignUpUserResponse;
import com.plim.plimserver.domain.user.dto.WithdrawUserRequest;
import com.plim.plimserver.domain.user.dto.WithdrawUserResponse;
import com.plim.plimserver.domain.user.service.UserService;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"User"})
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "사용자가 회원가입을 한다")
    @PostMapping("signup")
    public ResponseEntity<SignUpUserResponse> signup(@Valid @RequestBody SignUpUserRequest user) {
        User saved = userService.saveUser(user);
        return ResponseEntity.ok(SignUpUserResponse.builder()
                .email(saved.getEmail())
                .createdDate(saved.getCreatedDate())
                .build());
    }

    @ApiOperation(value = "회원탈퇴", notes = "회원을 탈퇴시킨다")
    @PostMapping("withdraw")
    public ResponseEntity<WithdrawUserResponse> withdraw(@AuthenticationPrincipal PrincipalDetails principal, @RequestBody WithdrawUserRequest dto) {
        User withdrew = userService.withdraw(principal, dto.getPassword());
        return ResponseEntity.ok(WithdrawUserResponse.builder()
                .email(withdrew.getEmail())
                .state(withdrew.getState())
                .build());
    }

}
