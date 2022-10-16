package kr.project.goodsorderfeature.core.security.jwt;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import kr.project.goodsorderfeature.core.error.BearerTokenInvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {

    private final ConfigurableJWTProcessor<SecurityContext> jwtProcessor;

    public Authentication authenticate(String token) throws AuthenticationException {
        try {
            return new UserDetailsJwtAuthenticationToken(jwtProcessor.process(token, null));
        } catch (BadJOSEException | JOSEException | ParseException e) {
            throw new BearerTokenInvalidException(e.getLocalizedMessage(), e);
        }
    }

}