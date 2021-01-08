package com.poogle.phog.web.admin.user.controller;

import com.poogle.phog.service.UserService;
import com.poogle.phog.web.admin.user.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseDTO> getAllUsers(@RequestParam(value = "admin") String name) {
        ResponseDTO responseDTO;
        if (checkAdmin(name)) {
            log.debug("[*] name: {}", name);
            responseDTO = userService.findUserList();
            return ResponseEntity.ok().body(responseDTO);
        } else {
            responseDTO = new ResponseDTO(401, "admin 인증 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
        }
    }

    private boolean checkAdmin(String name) {
        return name.equals("poogle");
    }

}
