package com.rvi.analyzer.rvianalyzerserver.controller;

import com.rvi.analyzer.rvianalyzerserver.domain.LoginRequest;
import com.rvi.analyzer.rvianalyzerserver.domain.LoginResponse;
import com.rvi.analyzer.rvianalyzerserver.domain.NewUserResponse;
import com.rvi.analyzer.rvianalyzerserver.dto.UserDto;
import com.rvi.analyzer.rvianalyzerserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    @PostMapping(path = "/register/user")
    public Mono<NewUserResponse> addUser(@RequestBody UserDto userDto, @RequestHeader("Authorization") String auth){
        return userService.addUser(userDto, auth);
    }

    @PostMapping(path = "/login/user")
    public Mono<ResponseEntity<LoginResponse>> loginUser(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }


    @GetMapping(path = "/rvi/analyzer/v1/user/{userName}")
    public Mono<UserDto> getUserInfo(@PathVariable String userName){
        return userService.getUserByUsername(userName);
    }
}
