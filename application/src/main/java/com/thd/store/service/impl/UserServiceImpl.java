package com.thd.store.service.impl;

import com.thd.store.repository.RoleRepository;
import com.thd.store.service.UserService;
import com.thd.store.dto.BaseResponse;
import com.thd.store.dto.user.UserDto;
import com.thd.store.entity.User;
import com.thd.store.repository.UserRepository;
import com.thd.store.util.SystemMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author DatNuclear 30/01/2024
 * @project store-movie
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public BaseResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            Optional<User> userOptional = userRepository.findByUsername(username);
            return getResponse200(new UserDto(userOptional.get()),getMessage(SystemMessage.SUCCESS));
        }
        return getResponse400(null);
    }

    @Override
    public BaseResponse getRoles() {
        return getResponse200(roleRepository.getAll(),getMessage(SystemMessage.SUCCESS));
    }
}
