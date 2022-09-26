package kr.submit.goodsorderfeature.core.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import kr.submit.goodsorderfeature.core.security.dto.Login;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.ServerErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.io.IOException;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final Validator validator;

    public JsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, Validator validator) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!HttpMethod.POST.matches(request.getMethod())) throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());

        try {
            final Login login = objectMapper.readValue(request.getInputStream(), Login.class);
            validator.validate(login);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login.getUserId(), login.getPassword());
            this.setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);

        } catch (IOException e) {
            throw new ServerErrorException("값을 확인하는데 실패하였습니다", e);
        }

    }
}