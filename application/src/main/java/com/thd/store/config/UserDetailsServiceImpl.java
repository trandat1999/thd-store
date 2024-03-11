package com.thd.store.config;

import com.thd.store.entity.Role;
import com.thd.store.entity.User;
import com.thd.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, ApplicationListener<AuthenticationSuccessEvent> {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled()
                , user.getAccountNonExpired(), user.getCredentialsNonExpired(), user.getAccountNonLocked(), grantedAuthorities( user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> grantedAuthorities(Set<Role> roles) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(role.getName()));
        }
        return list;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        String userName = ((UserDetails) authenticationSuccessEvent.getAuthentication().
                getPrincipal()).getUsername();
        Optional<User> userOptional = userRepository.findByUsername(userName);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("username not found: " + userName));
        user.setLastLogin(new Date());
        userRepository.save(user);
    }
}
