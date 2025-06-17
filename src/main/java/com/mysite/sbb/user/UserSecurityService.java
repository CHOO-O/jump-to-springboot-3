package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
// 스프링 시큐리티가 로그인 시 사용할 서비스
// 이 서비스는 UserDetailsService 인터페이스를 구현해야 함.
//           ㄴ loadUserByUsername 메서드(username으로 스프링 시큐리티의 user 객체를 조회하여 리턴함)를 구현하도록 강제하는 인터페이스
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<SiteUser> _siteUser = this.userRepository.findByUsername(username);
        if(_siteUser.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        SiteUser siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>(); // 사용자 권한 정보를 나타내는 GrandtedAuthority 객체 생성에 사용
        if("admin".equals(username)){ // 사용자명이 admin이면 ADMIN권한 줌
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else { // 이외에는 USER권한 줌
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        // 이 메서드로 리턴된 user객체의 비밀번호와 입력받은 비밀번호의 비교 기능은 스프링 시큐리티 내부에 가지고 있음!
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
