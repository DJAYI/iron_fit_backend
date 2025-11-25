package com.iron_fit.iron_fit_backend.features.people_management.clients.application.services;

import com.iron_fit.iron_fit_backend.features.auth.domain.entities.UserEntity;
import com.iron_fit.iron_fit_backend.features.auth.infrastructure.repositories.UserRepository;
import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.clients.infrastructure.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetClientFromSession {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    public Long execute () {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<UserEntity> userAuthenticated = userRepository.findByUsername(username);
        if (userAuthenticated.isEmpty()) throw new UsernameNotFoundException(username);

        try {
             Optional<ClientEntity> clientEntityOptional =clientRepository.findByUser_Id(userAuthenticated.get().getId());
             if (clientEntityOptional.isEmpty()) throw new Exception("Not found client associated to user authenticated");

             return clientEntityOptional.get().getId();

        } catch (Exception e) {
           return null;
        }
    }
}
