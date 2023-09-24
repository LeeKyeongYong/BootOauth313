package com.social.demo3.base.rq;

import com.social.demo3.domain.entity.Member;
import com.social.demo3.domain.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.security.core.userdetails.User;
@Component
@RequestScope
public class Rq {

    private final MemberService memberService;
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final User user;
    private final HttpSession session;
    private Member member = null; //레이지 로딩, 처음부터 삽인안하고 요청이 들어올때 삽입한다.

    public Rq(MemberService memberService, HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        this.memberService = memberService;
        this.req = req;
        this.resp = resp;
        this.session = session;

        //현재 로그인한 회원의 인증정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getPrincipal() instanceof User){
            this.user = (User)authentication.getPrincipal();
        } else {
            this.user = null;
        }
    }

    //로그인이 되어있는지 체크
        public boolean isLogin(){
            return user!=null;
        }
    //로그아웃이 되어있는지 체크
        public boolean isLogout(){
         return !isLogin();
        }
    //로그인 된 회원의 객체
        public Member getMember(){
            if(isLogout()) return null;

            //데이터가 없는지 체크
            if(member == null){
                member = memberService.findByUsername(user.getUsername()).orElseThrow();
            }
            return member;
        }
}
