package kr.project.goodsorderfeature.core.jpa.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@EnableJpaAuditing
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    private static final String ANONYMOUS_USER = "anonymousUser";

    private static boolean notEqualsAnonymousUser(String value) {
        return !ANONYMOUS_USER.equalsIgnoreCase(value);
    }

    @Override
    public Optional<String> getCurrentAuditor() {

        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName)
                .filter(AuditorAwareImpl::notEqualsAnonymousUser)
                .map(String::valueOf);
    }
}