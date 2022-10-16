package kr.project.goodsorderfeature.core.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import kr.project.goodsorderfeature.core.security.jwt.JwtTokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    public static final String TOKEN_KEY = "token", TOKEN_TYPE_KEY = "type";

    private final JwtTokenGenerator jwtTokenGenerator;
    private final ObjectMapper objectMapper;

    @SneakyThrows(JOSEException.class)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ServletOutputStream outputStream = response.getOutputStream();

        final Object principal = authentication.getPrincipal();
        if(principal instanceof User) {
            final User user = (User) principal;
            final Map<String, String> tokenResponse = Map.of(TOKEN_KEY, jwtTokenGenerator.generate(user), TOKEN_TYPE_KEY, JwtTokenGenerator.TOKEN_TYPE_VALUE);
            outputStream.write(objectMapper.writeValueAsBytes(tokenResponse));
        }

        outputStream.flush();
    }
}