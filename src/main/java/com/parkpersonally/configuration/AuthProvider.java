package com.parkpersonally.configuration;

import com.parkpersonally.exception.IllegalAuthException;
import com.parkpersonally.model.*;
import com.parkpersonally.repository.AdministratorRepository;
import com.parkpersonally.repository.CustomerRepository;
import com.parkpersonally.repository.ManagerRepository;
import com.parkpersonally.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        String actualName = "";
        String actualPassword = "";
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            String role = grantedAuthority.getAuthority();
            if (role.equals(AccountCredentials.ROLE_CUSTOMER)) {
                Customer customer = customerRepository.findParkingBoyByNameEqualsAndAndPasswordEquals(name, password);
                actualName = customer.getName();
                actualPassword = customer.getPassword();

            } else if (role.equals(AccountCredentials.ROLE_MANAGER)) {
                Manager manager = managerRepository.findParkingBoyByNameEqualsAndAndPasswordEquals(name, password);
                actualName = manager.getName();
                actualPassword = manager.getPassword();

            } else if (role.equals(AccountCredentials.ROLE_PARKING_BOY)) {
                ParkingBoy parkingBoy = parkingBoyRepository.findParkingBoyByNameEqualsAndAndPasswordEquals(name, password);
                actualName = parkingBoy.getName();
                actualPassword = parkingBoy.getPassword();

            } else if (role.equals(AccountCredentials.ROLE_ADMIN)) {
                Administrator administrator = administratorRepository.findParkingBoyByNameEqualsAndAndPasswordEquals(name, password);
                actualName = administrator.getName();
                actualPassword = administrator.getPassword();
            }
        }

        if (name.equals(actualName) && password.equals(actualPassword)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new GrantedAuthorityImpl(AccountCredentials.ROLE_ADMIN));
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
            return auth;
        } else {
            throw new IllegalAuthException("密码或者账号错误");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
