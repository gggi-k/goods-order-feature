package kr.submit.goodsorderfeature.core.security.jwt;


import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class UserDetailsJwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails userDetails;

    public UserDetailsJwtAuthenticationToken(JWTClaimsSet claimsSet) {
        super(Collections.emptyList());
        setAuthenticated(true);
        this.userDetails = new User(claimsSet.getSubject(), "", Collections.emptyList());
    }

    @Override
    public Object getCredentials() {
        return this.userDetails.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return this.userDetails;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority.class::cast)
                .collect(Collectors.toList());
    }
}