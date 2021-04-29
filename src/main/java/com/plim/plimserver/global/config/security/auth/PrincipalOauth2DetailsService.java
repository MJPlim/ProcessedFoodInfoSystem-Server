package com.plim.plimserver.global.config.security.auth;

import com.plim.plimserver.domain.user.domain.User;
import com.plim.plimserver.domain.user.domain.UserProvider;
import com.plim.plimserver.domain.user.domain.UserRoleType;
import com.plim.plimserver.domain.user.domain.UserStateType;
import com.plim.plimserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2DetailsService extends DefaultOAuth2UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            String provider = userRequest.getClientRegistration().getClientName();
            UserProvider userProvider = null;
            if (provider.equals("Google")) userProvider = UserProvider.GOOGLE;
            else if (provider.equals("Kakao")) userProvider = UserProvider.KAKAO;
            return userRepository.save(User.builder()
                    .email(email)
                    .password(passwordEncoder.encode("KATI"))
                    .name(oAuth2User.getAttribute("name"))
                    .role(UserRoleType.ROLE_USER)
                    .state(UserStateType.NORMAL)
                    .provider(userProvider)
                    .build());
        });

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

}
