package com.example.AuthenticationProject.service;

import com.example.AuthenticationProject.entity.User;
import com.example.AuthenticationProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public void delete(Long id) throws Exception {
        Optional<User> findById = userRepository.findById(id);
        if(findById.isPresent()){
            userRepository.deleteById(id);
        }
        else{
            throw new BadRequestException("Bad Credentials");
        }
    }
}
