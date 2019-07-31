package com.parkpersonally.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class GrantedAuthorityImpl implements GrantedAuthority {

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }
}
