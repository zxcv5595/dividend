package com.example.dividend.service;

import com.example.dividend.domain.Member;
import com.example.dividend.dto.Auth;
import com.example.dividend.exception.SecurityException;
import com.example.dividend.repository.MemberRepository;
import com.example.dividend.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.dividend.type.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find user ->" + username));
    }

    public Member register(Auth.SignUp member) {
        boolean exists = memberRepository.existsByUsername(member.getUsername());
        if (exists) {
            throw new SecurityException(ALREADY_EXIST_USER);
        }

        member.setPassword(passwordEncoder.encode(member.getPassword()));

        return memberRepository.save(member.fromEntity());
    }

    public Member authenticate(Auth.SignIn member) {
        Member user = memberRepository.findByUsername(member.getUsername())
                .orElseThrow(() -> new SecurityException(NOT_EXIST_ID));

        if(!passwordEncoder.matches(member.getPassword(), user.getPassword())){
            throw new SecurityException(NOT_MATCHED_PASSWORD);
        }
        return user;
    }
}
