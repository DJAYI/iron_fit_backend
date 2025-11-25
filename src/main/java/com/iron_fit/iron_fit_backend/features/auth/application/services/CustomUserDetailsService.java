package com.iron_fit.iron_fit_backend.features.auth.application.services;

import com.iron_fit.iron_fit_backend.features.auth.domain.entities.UserEntity;
import com.iron_fit.iron_fit_backend.features.auth.infrastructure.repositories.UserRepository;
import com.iron_fit.iron_fit_backend.core.utils.JwtUtils;
import com.iron_fit.iron_fit_backend.features.auth.domain.dto.login.LoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username not found"));
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoleEntities().forEach(role -> {
            authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName())));
        });

        return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getIsEnabled(), userEntity.getIsAccountNoExpired(), userEntity.getIsCredentialNoExpired(), userEntity.getIsAccountNoLocked(), authorityList);
    }

    public HashMap<String, Object> loginUser(LoginRequestDto authLoginRequest) {

        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        try {
            Authentication authentication = this.authenticate(username, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtUtils.createToken(authentication);

            return new HashMap<String, Object>() {{
               put("token", accessToken);
               put("role", authentication.getAuthorities().toString());
               put("username", username);
            }};
        } catch (Error e) {
            return new  HashMap<String, Object>() {{
                put("error", e.getMessage());
            }};
        }
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }
}
