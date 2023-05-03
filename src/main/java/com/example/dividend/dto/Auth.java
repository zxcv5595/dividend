package com.example.dividend.dto;

import com.example.dividend.domain.Member;
import lombok.*;

import java.util.List;

public class Auth {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignIn {

        private String username;
        private String password;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUp {

        private String username;
        private String password;
        private List<String> roles;

        public Member fromEntity() {
            return Member.builder()
                    .username(username)
                    .password(password)
                    .roles(roles)
                    .build();
        }
    }
}
