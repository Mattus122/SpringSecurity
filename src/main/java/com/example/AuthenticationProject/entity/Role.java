package com.example.AuthenticationProject.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.List;


@RequiredArgsConstructor
public enum Role {
    ADMIN , MEMBER

}
