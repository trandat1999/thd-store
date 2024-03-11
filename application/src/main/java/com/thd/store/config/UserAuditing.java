package com.thd.store.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

public class UserAuditing implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String name = "anonymousUser";
        if (!ObjectUtils.isEmpty(SecurityContextHolder.getContext()) && !ObjectUtils.isEmpty(SecurityContextHolder.getContext().getAuthentication()) &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            name = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return Optional.of(name);
    }
}
