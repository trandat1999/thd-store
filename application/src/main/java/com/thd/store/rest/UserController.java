package com.thd.store.rest;

import com.thd.store.service.UserService;
import com.thd.store.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DatNuclear 30/01/2024
 * @project store-movie
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/current")
    public ResponseEntity<BaseResponse> getCurrentUser(){
        return ResponseEntity.ok(userService.getCurrentUser());
    }
}
