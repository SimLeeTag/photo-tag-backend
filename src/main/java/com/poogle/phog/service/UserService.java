package com.poogle.phog.service;

import com.poogle.phog.domain.User;
import com.poogle.phog.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insertOrUpdate(User user) {
        Optional<User> currentUser = userRepository.findUserBySocialId(user.getSocialId());
        if (currentUser.isPresent()) {
            currentUser.get().setName(user.getName());
            currentUser.get().setEmail(user.getEmail());
            userRepository.save(currentUser.get());
        } else {
            userRepository.save(user);
        }
    }
}
