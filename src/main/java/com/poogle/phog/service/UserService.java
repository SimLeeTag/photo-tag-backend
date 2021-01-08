package com.poogle.phog.service;

import com.poogle.phog.domain.User;
import com.poogle.phog.domain.UserRepository;
import com.poogle.phog.web.admin.user.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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

    public ResponseDTO findUserList() {
        List<User> users = userRepository.findAll();
        log.debug("[*] users: {}", users);
        return new ResponseDTO(200, "전체 유저 목록", users);
    }
}
