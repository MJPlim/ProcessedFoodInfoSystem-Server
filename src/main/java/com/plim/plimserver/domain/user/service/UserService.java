package com.plim.plimserver.domain.user.service;

import com.plim.plimserver.domain.favorite.repository.FavoriteRepository;
import com.plim.plimserver.domain.post.repository.CommentRepository;
import com.plim.plimserver.domain.review.repository.ReviewRepository;
import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.dto.*;
import com.plim.plimserver.domain.user.exception.*;
import com.plim.plimserver.domain.user.repository.UserRepository;
import com.plim.plimserver.global.config.security.auth.PrincipalDetails;
import com.plim.plimserver.global.domain.mail.domain.EmailAuthCode;
import com.plim.plimserver.global.domain.mail.domain.EmailSubject;
import com.plim.plimserver.global.domain.mail.repository.EmailAuthCodeRepository;
import com.plim.plimserver.global.domain.mail.util.EmailAuthCodeGenerator;
import com.plim.plimserver.global.domain.mail.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailAuthCodeRepository emailAuthCodeRepository;
    private final FavoriteRepository favoriteRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final EmailUtil emailUtil;

    public User saveUser(SignUpUserRequest dto) {
        if (emailAuthCodeRepository.existsByEmail(dto.getEmail()))
            throw new EmailNotVerifiedException(UserExceptionMessage.EMAIL_NOT_VERIFIED_EXCEPTION_MESSAGE);
        if (userRepository.existsByEmail(dto.getEmail()))
            throw new EmailDuplicateException(UserExceptionMessage.EMAIL_DUPLICATE_EXCEPTION_MESSAGE);

        EmailAuthCodeGenerator authCodeGenerator = new EmailAuthCodeGenerator();
        String authCode = authCodeGenerator.generateAuthCode();
        emailAuthCodeRepository.save(EmailAuthCode.builder()
                .email(dto.getEmail())
                .authCode(authCode)
                .build());

        String message = emailUtil.getEmailAuthMessage(dto.getEmail(), authCode);
        emailUtil.sendEmail(dto.getEmail(), EmailSubject.EMAIL_AUTH_REQUEST, message);

        return userRepository.save(dto.toEntity(passwordEncoder));
    }

    @Transactional
    public User withdraw(PrincipalDetails principal, String password) {
        User user = getUser(principal);
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches)
            throw new PasswordMismatchException(UserExceptionMessage.PASSWORD_MISMATCH_EXCEPTION_MESSAGE);
        user.withdraw();
        return user;
    }

    @Transactional
    public User setSecondEmail(PrincipalDetails principal, SetSecondEmailRequest request) {
        if (userRepository.existsBySecondEmail(request.getSecondEmail()))
            throw new SecondEmailDuplicateException(UserExceptionMessage.SECOND_EMAIL_DUPLICATE_EXCEPTION_MESSAGE);

        User user = getUser(principal);
        user.setSecondEmail(request.getSecondEmail());

        return user;
    }

    public User findEmail(FindEmailRequest request) {
        User user = this.userRepository.findBySecondEmail(request.getSecondEmail())
                .orElseThrow(() -> new UsernameNotFoundException(UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));

        String msg = this.emailUtil.getFindEmailMessage(user.getEmail());
        this.emailUtil.sendEmail(request.getSecondEmail(), EmailSubject.FIND_EMAIL_REQUEST, msg);

        return user;
    }

    @Transactional
    public User findPassword(FindPasswordRequest dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));

        EmailAuthCodeGenerator authCodeGenerator = new EmailAuthCodeGenerator();
        String authCode = authCodeGenerator.generateAuthCode();
        user.updatePassword(passwordEncoder.encode(authCode));

        String message = emailUtil.getFindPasswordMessage(dto.getEmail(), authCode);
        emailUtil.sendEmail(dto.getEmail(), EmailSubject.FIND_PASSWORD_REQUEST, message);

        return user;
    }

    @Transactional
    public User modifyPassword(PrincipalDetails principal, ModifyPasswordRequest dto) {
        User user = getUser(principal);
        //입력한 비밀번호가 기존 비밀번호와 다를때
        boolean matches1 = passwordEncoder.matches(dto.getBeforePassword(), user.getPassword());
        if (!matches1)
            throw new PasswordMismatchException(UserExceptionMessage.PASSWORD_MISMATCH_EXCEPTION_MESSAGE);

        //변경할 비밀번호가 기존 비밀번호와 같을때
        boolean matches2 = passwordEncoder.matches(dto.getAfterPassword(), user.getPassword());
        if (matches2)
            throw new PasswordDuplicatedException(UserExceptionMessage.PASSWORD_DUPLICATED_EXCEPTION_MESSAGE);

        user.updatePassword(passwordEncoder.encode(dto.getAfterPassword()));

        return user;
    }

    public ResponseEntity<UserInfoResponse> getUserInfo(PrincipalDetails principal) {
        return ResponseEntity.ok(UserInfoResponse.from(getUser(principal)));
    }

    @Transactional
    public ResponseEntity<UserInfoResponse> modifyUserInfo(PrincipalDetails principal, UserInfoModifyRequest request) {
        return ResponseEntity.ok(UserInfoResponse.from(getUser(principal).modifyUserInfo(request)));
    }

    public ResponseEntity<UserSummaryResponse> userSummary(PrincipalDetails principal) {
        User user = getUser(principal);
        Long favoriteCount = this.favoriteRepository.countAllByUser(user);
        Long reviewCount = this.reviewRepository.countAllByUser(user);
        return ResponseEntity.ok(UserSummaryResponse.of(user.getName(), favoriteCount, reviewCount));
    }

    private User getUser(PrincipalDetails principal) {
        return userRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
    }

    public ResponseEntity<GetSecondEmailResponse> getSecondEmail(PrincipalDetails principalDetails) {
        User user = getUser(principalDetails);
        return ResponseEntity.ok(GetSecondEmailResponse.builder().secondEmail(user.getSecondEmail()).build());
    }
}
