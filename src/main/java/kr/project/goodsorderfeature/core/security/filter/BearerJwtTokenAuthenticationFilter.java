package kr.project.goodsorderfeature.core.security.filter;


import kr.project.goodsorderfeature.core.error.BearerTokenInvalidException;
import kr.project.goodsorderfeature.core.security.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class BearerJwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = this.resolve(request);
        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(jwtAuthenticationProvider.authenticate(token));
        SecurityContextHolder.setContext(securityContext);

        filterChain.doFilter(request, response);
    }

    private String resolve(HttpServletRequest request) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.startsWithIgnoreCase(token, "bearer")) {
            return null;
        }
        Matcher matcher = authorizationPattern.matcher(token);
        if(!matcher.matches()) {
            throw new BearerTokenInvalidException("잘못된 토큰입니다");
        }

        return matcher.group("token");
    }

}