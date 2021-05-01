package com.plim.plimserver.domain.user.api;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.dto.FindPasswordRequest;
import com.plim.plimserver.domain.user.dto.FindPasswordResponse;
import com.plim.plimserver.domain.user.dto.ModifyPasswordRequest;
import com.plim.plimserver.domain.user.dto.ModifyPasswordResponse;
import com.plim.plimserver.domain.user.dto.SignUpUserRequest;
import com.plim.plimserver.domain.user.dto.SignUpUserResponse;
import com.plim.plimserver.domain.user.dto.WithdrawUserRequest;
import com.plim.plimserver.domain.user.dto.WithdrawUserResponse;
import com.plim.plimserver.domain.user.service.UserService;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"User"})
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "사용자가 회원가입을 한다")
    @PostMapping("signup")
    public ResponseEntity<SignUpUserResponse> signup(@Valid @RequestBody SignUpUserRequest dto) {
        User saved = userService.saveUser(dto);
        return ResponseEntity.ok(SignUpUserResponse.builder()
                .email(saved.getEmail())
                .message("해당 메일 주소로 이메일 인증 메일을 발송했습니다. 메일 인증을 하시면 회원가입이 완료됩니다.")
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

    @ApiOperation(value = "패스워드 찾기", notes = "해당 메일 주소로 임시 패스워드를 전송한다.")
    @PostMapping("find-password")
    public ResponseEntity<FindPasswordResponse> findPassword(@Valid @RequestBody FindPasswordRequest dto) {
        User user = userService.findPassword(dto);
        return ResponseEntity.ok(FindPasswordResponse.builder()
                .email(user.getEmail())
                .build());
    }

    @GetMapping("oauth-success")
    public ResponseEntity<String> user(HttpServletResponse response, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails == null) {
            throw new IllegalArgumentException("로그인을 해주세요.");
        } else if (!principalDetails.isEnabled()) {
            throw new IllegalArgumentException("회원탈퇴된 계정입니다.");
        }
        return ResponseEntity.ok("로그인 성공");
    }
    
    @ApiOperation(value = "패스워드 변경", notes = "패스워드 변경을 요청한다.")
    @PostMapping("modify-password")					//이전 비밀번호와 새로운 비밀번호를 받는다. 
    public ResponseEntity<ModifyPasswordResponse> modifyPassword(@AuthenticationPrincipal PrincipalDetails principal,
    		@Valid @RequestBody ModifyPasswordRequest dto) {
        User user = userService.modifyPassword(principal, dto);
        return ResponseEntity.ok(ModifyPasswordResponse.builder()
        		.message("패스워드 변경 완료")
        		.build());
    }

//    @PostMapping("enter-MyTab")					//유저의 패스워드를 입력받아 검증한다.  
//    public ResponseEntity<EnterMyTabResponse> enterMyTab(@AuthenticationPrincipal PrincipalDetails principal,
//    		@Valid @RequestBody EnterMyTabRequest dto) {
//        User user = userService.enterMyTab(principal, dto);
//        return ResponseEntity.ok(EnterMyTabResponse.builder()
//        		.enterCode(1)
//        		.userName(user.getName())
//        		.message("들어가십쇼")
//        		.build());
//    }
    
    
    
}
