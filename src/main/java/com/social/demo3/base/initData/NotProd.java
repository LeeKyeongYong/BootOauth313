package com.social.demo3.base.initData;

import com.social.demo3.domain.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.stream.IntStream;

@Configuration
@Profile("!prod")
public class NotProd {

    @Value("${custom.security.oauth2.client.registration.kakao.devUser.oauthId}")
    private String kakaoDevUserOAuthId;

    @Value("${custom.security.oauth2.client.registration.kakao.devuser.nickname}")
    private String kakaoDevUserNickname;

    @Value("${custom.security.oauth2.client.registration.kakao.devUser.profileImgUrl}")
    private String kakaoDevUserProfileImgUrl;

    @Bean
    public ApplicationRunner init(MemberService memberService){
        return args -> {
            memberService.join("admin","1234","admin","");

            IntStream.rangeClosed(1,3).forEach(i ->{
                memberService.join("user"+i,"1234","nickname"+i,"");
            });

            memberService.whenSocialLogin(
                    "KAKAO",
                    "KAKAO__%s".formatted(kakaoDevUserOAuthId),
                    kakaoDevUserNickname,
                    kakaoDevUserProfileImgUrl
            );
        };
    }
}
