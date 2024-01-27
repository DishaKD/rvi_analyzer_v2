package com.rvi.analyzer.rvianalyzerserver.service;

import com.rvi.analyzer.rvianalyzerserver.domain.*;
import com.rvi.analyzer.rvianalyzerserver.dto.StyleDto;
import com.rvi.analyzer.rvianalyzerserver.dto.UserDto;
import com.rvi.analyzer.rvianalyzerserver.entiy.Style;
import com.rvi.analyzer.rvianalyzerserver.mappers.StyleMapper;
import com.rvi.analyzer.rvianalyzerserver.repository.StyleRepository;
import com.rvi.analyzer.rvianalyzerserver.repository.UserRepository;
import com.rvi.analyzer.rvianalyzerserver.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StyleService {
    final private StyleRepository styleRepository;
    final private UserRepository userRepository;
    final private StyleMapper styleMapper;
    final private JwtUtils jwtUtils;
    final private UserGroupRoleService userGroupRoleService;

    public Mono<NewStyleResponse> addStyle(StyleDto styleDto, String jwt) {
        return Mono.just(styleDto)
                .doOnNext(styleDto1 -> log.info("Style add request received [{}]", styleDto))
                .flatMap(request -> styleRepository.findByName(request.getName()))
                .flatMap(style -> Mono.just(NewStyleResponse.builder()
                        .status("E1002")
                        .statusDescription("Style Already exists")
                        .build()))
                .switchIfEmpty(
                        createStyle(styleDto, jwtUtils.getUsername(jwt))
                )
                .doOnError(e ->
                        NewStyleResponse.builder()
                                .status("E1000")
                                .statusDescription("Failed")
                                .build());
    }

    private Mono<NewStyleResponse> createStyle(StyleDto styleDto, String username) {
        return userRepository.findByUsername(username)
                .flatMap(creatingStyle -> userGroupRoleService.getUserRolesByUserGroup(creatingStyle.getGroup())
                        .flatMap(userRoles -> {
                            log.info(styleDto.getName());
                            if (userRoles.contains(UserRoles.CREATE_TOP_ADMIN)) {
                                return save(styleDto, username);
                            } else {
                                return Mono.just(NewStyleResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build());
                            }
                        }));
    }

    private Mono<NewStyleResponse> save(StyleDto styleDto, String username) {
        return Mono.just(styleDto)
                .map(styleMapper::styleDtoToStyle)
                .doOnNext(style -> {
                    style.setName(styleDto.getName());
                    style.setCustomer(styleDto.getCustomer() != null ? styleDto.getCustomer() : "UN-ASSIGNED");
                    style.setPlant(styleDto.getPlant() != null ? styleDto.getPlant() : "UN-ASSIGNED");
                    style.setAdmin(styleDto.getAdmin() != null ? styleDto.getAdmin() : new ArrayList<>());
                    style.setCreatedBy(username);
                    style.setCreatedDateTime(LocalDateTime.now());
                })
                .flatMap(styleRepository::insert)
                .doOnSuccess(style -> log.info("Successfully saved the style [{}]", style))
                .map(style -> NewStyleResponse.builder()
                        .status("S1000")
                        .statusDescription("Success")
                        .name(styleDto.getName())
                        .build());
    }

    public Mono<ResponseEntity<CommonResponse>> allocateAdmin(UpdateStyle updateStyle, String jwt) {
        return userRepository.findByUsername(jwtUtils.getUsername(jwt))
                .flatMap(requestedUser -> userGroupRoleService.getUserRolesByUserGroup(requestedUser.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.CREATE_TOP_ADMIN)) {
                                return styleRepository.findByName(updateStyle.getName())
                                        .flatMap(style -> {
                                            List<String> admins = style.getAdmin();
                                            if (!admins.contains(updateStyle.getAdmin())) {
                                                admins.add(updateStyle.getAdmin());
                                            }
                                            style.setAdmin(admins);
                                            return styleRepository.save(style)
                                                    .flatMap(style1 -> Mono.just(
                                                            ResponseEntity.ok(CommonResponse.builder()
                                                                    .status("S1000")
                                                                    .statusDescription("Success").build(
                                                                    )
                                                            )));
                                        })
                                        .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                                                .status("E1000")
                                                .statusDescription("Failed").build())));
                            } else {
                                System.out.println("02");
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                        .status("E1000")
                        .statusDescription("Failed").build())));
    }

    public Mono<ResponseEntity<CommonResponse>> updateStyle(UpdateStyle updateStyle, String jwt) {
        return userRepository.findByUsername(jwtUtils.getUsername(jwt))
                .flatMap(requestedUser -> userGroupRoleService.getUserRolesByUserGroup(requestedUser.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.CREATE_TOP_ADMIN)) {
                                return styleRepository.findByName(updateStyle.getName())
                                        .flatMap(style -> {
                                            List<String> admins = style.getAdmin();
                                            admins.add(updateStyle.getAdmin());
                                            style.setAdmin(admins);
                                            style.setPlant(updateStyle.getPlant());
                                            style.setCustomer(updateStyle.getCustomer());
                                            return styleRepository.save(style)
                                                    .flatMap(style1 -> Mono.just(
                                                            ResponseEntity.ok(CommonResponse.builder()
                                                                    .status("S1000")
                                                                    .statusDescription("Success").build(
                                                                    )
                                                            )));
                                        })
                                        .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                                                .status("E1000")
                                                .statusDescription("Failed").build())));
                            } else {
                                System.out.println("02");
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                        .status("E1000")
                        .statusDescription("Failed").build())));
    }

    public Mono<Style> getStyle(String name) {
        return Mono.just(name)
                .doOnNext(s -> log.info("Finding style by name [{}]", s))
                .flatMap(styleRepository::findByName);
    }

    public Mono<ResponseEntity<StyleResponse>> getStyles(String auth) {
        log.info("get styles request received with jwt [{}]", auth);
        return userRepository.findByUsername(jwtUtils.getUsername(auth))
                .flatMap(requestedUser -> userGroupRoleService.getUserRolesByUserGroup(requestedUser.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.GET_ALL_USERS)) {
                                return styleRepository.findAll()
                                        .map(style -> {
                                            return StyleDto.builder()
                                                    ._id(style.get_id())
                                                    .plant(style.getPlant())
                                                    .customer(style.getCustomer())
                                                    .name(style.getName())
                                                    .admin(style.getAdmin())
                                                    .createdBy(style.getCreatedBy())
                                                    .createdDateTime(style.getCreatedDateTime())
                                                    .build();
                                        })
                                        .collectList()
                                        .flatMap(styleDtos -> Mono.just(ResponseEntity.ok(StyleResponse.builder()
                                                .status("S1000")
                                                .statusDescription("Success")
                                                .styles(styleDtos)
                                                .build()
                                        )));
                            } else if (userRoles.contains(UserRoles.GET_USERS)) {
                                return styleRepository.findByCreatedBy(requestedUser.getUsername())
                                        .map(style -> {
                                            return StyleDto.builder()
                                                    ._id(style.get_id())
                                                    .plant(style.getPlant())
                                                    .customer(style.getCustomer())
                                                    .name(style.getName())
                                                    .admin(style.getAdmin())
                                                    .createdBy(style.getCreatedBy())
                                                    .createdDateTime(style.getCreatedDateTime())
                                                    .build();
                                        })
                                        .collectList()
                                        .flatMap(styleDtos -> Mono.just(ResponseEntity.ok(StyleResponse.builder()
                                                .status("S1000")
                                                .statusDescription("Success")
                                                .styles(styleDtos)
                                                .build()
                                        )));
                            } else {
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(StyleResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(StyleResponse.builder()
                        .status("E1000")
                        .statusDescription("Failed").build())));
    }
}
