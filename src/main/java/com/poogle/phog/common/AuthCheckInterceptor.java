package com.poogle.phog.common;

import com.poogle.phog.domain.User;
import com.poogle.phog.domain.UserRepository;
import com.poogle.phog.exception.AuthorizationException;
import com.poogle.phog.service.JwtService;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class AuthCheckInterceptor implements HandlerInterceptor {
    private final String HEADER_AUTH = "Authorization";
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthCheckInterceptor(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String jwt = request.getHeader(HEADER_AUTH);
        if (jwt == null) {
            throw AuthorizationException.emptyToken();
        }
        Map<String, Object> payloads = jwtService.getTokenPayload(jwt);
        String socialId = jwtService.parseAppleJwt(payloads).getSocialId();
        User user = userRepository.findUserBySocialId(socialId).orElseThrow(NotFound::new);
        request.setAttribute("id", user.getId());
        return true;
    }
}
