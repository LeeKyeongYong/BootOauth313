package com.social.demo3.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String profileImgUrl;

    public List<? extends GrantedAuthority> getGrantedAuthorities(){

        List<GrantedAuthority> grantedAuthorities =new ArrayList<>();

        //모든 멤버는 member권한을 가진다.
        grantedAuthorities.add(new SimpleGrantedAuthority("member"));

        //username이 admin인 회원은 추가로 admin 권하도 가진다.
        if(isAdmin()){
            grantedAuthorities.add(new SimpleGrantedAuthority("admin"));
        }
        return grantedAuthorities;
    }

    public boolean isAdmin(){
        return "admin".equals(username);
    }

}
