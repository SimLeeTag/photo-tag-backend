package com.poogle.phog.web.login.controller;

import com.poogle.phog.domain.User;
import com.poogle.phog.service.JwtService;
import com.poogle.phog.service.UserService;
import com.poogle.phog.web.login.dto.PostRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class LoginController {

    private final JwtService jwtService;
    private final UserService userService;

    public LoginController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/apple-login")
    public void appleLogin(@RequestBody PostRequestDTO postRequestDTO,
                           HttpServletResponse response) {
        Map<String, Object> payloads = jwtService.getTokenPayload(postRequestDTO.getJwtToken());
        if (!jwtService.checkAppleValidation(payloads)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        User user = jwtService.parseAppleJwt(payloads);
        userService.insertOrUpdate(user);
        response.setStatus(HttpStatus.OK.value());
    }
}
