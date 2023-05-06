package com.rvi.analyzer.rvianalyzerserver.service;

import com.rvi.analyzer.rvianalyzerserver.domain.*;
import com.rvi.analyzer.rvianalyzerserver.dto.*;
import com.rvi.analyzer.rvianalyzerserver.entiy.ModeOne;
import com.rvi.analyzer.rvianalyzerserver.entiy.ModeTwo;
import com.rvi.analyzer.rvianalyzerserver.mappers.*;
import com.rvi.analyzer.rvianalyzerserver.repository.*;
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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    final private ModeOneMapper modeOneMapper;
    final private ModeTwoMapper modeTwoMapper;
    final private ModeThreeMapper modeThreeMapper;
    final private ModeFourMapper modeFourMapper;
    final private ModeFiveMapper modeFiveMapper;
    final private ModeSixMapper modeSixMapper;
    final private ModeOneRepository modeOneRepository;
    final private ModeTwoRepository modeTwoRepository;
    final private ModeThreeRepository modeThreeRepository;
    final private ModeFourRepository modeFourRepository;
    final private ModeFiveRepository modeFiveRepository;
    final private ModeSixRepository modeSixRepository;
    final private JwtUtils jwtUtils;
    final private UserService userService;
    final private UserGroupRoleService userGroupRoleService;

    public Mono<ResponseEntity<CommonResponse>> addModeOne(ModeOneDto modeOneDto, String jwt) {
        return userService.getUser(jwtUtils.getUsername(jwt))
                .flatMap(user -> userGroupRoleService.getUserRolesByUserGroup(user.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.SAVE_MODE_ONE)) {
                                return Mono.just(modeOneDto)
                                        .doOnNext(modeOneDto1 -> log.info("MOde one add request received [{}]", modeOneDto1))
                                        .flatMap(modeOneDto1 -> modeOneRepository.findBySessionID(modeOneDto1.getDefaultConfigurations().getSessionId()))
                                        .flatMap(modeOne -> Mono.just(modeOne)
                                                .filter(mOne -> mOne.getResults().stream().anyMatch(i -> Objects.equals(i.getTestId(), modeOneDto.getResults().get(0).getTestId())))
                                                .flatMap(modeOne1 ->
                                                        Mono.just(ResponseEntity.ok(CommonResponse.builder()
                                                                .status("E1010")
                                                                .statusDescription("Mode Already exist with taskID")
                                                                .build()))
                                                ).switchIfEmpty(updateSessionOne(modeOneDto, modeOne))
                                        )
                                        .switchIfEmpty(saveModeOne(modeOneDto, jwt))
                                        .doOnError(e ->
                                                ResponseEntity.ok(CommonResponse.builder()
                                                        .status("E1000")
                                                        .statusDescription("Failed")
                                                        .build()));
                            } else {
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                        .status("E1200")
                        .statusDescription("Failed")
                        .build())));


    }

    private Mono<ResponseEntity<CommonResponse>> updateSessionOne(ModeOneDto modeOneDto, ModeOne modeOne) {
        return Mono.just(modeOne)
                .doOnNext(modeOne1 -> {
                    modeOneDto.getResults().get(0).getReadings().get(0).setReadAt(LocalDateTime.now());
                    modeOne.getResults().add(modeOneDto.getResults().get(0));
                    modeOne.setLastUpdatedDateTime(LocalDateTime.now());
                })
                .flatMap(modeOneRepository::save)
                .doOnSuccess(mOne -> log.info("Successfully updated the Mode One [{}]", mOne))
                .map(device -> ResponseEntity.ok(CommonResponse.builder()
                        .status("S1000")
                        .statusDescription("Success")
                        .build()));
    }

    private Mono<ResponseEntity<CommonResponse>> saveModeOne(ModeOneDto modeOneDto, String jwt) {
        return Mono.just(modeOneMapper.modeOneDtoToModeOne(modeOneDto))
                .doOnNext(modeOne -> {
                    modeOne.setCreatedBy(jwtUtils.getUsername(jwt));
                    modeOne.setStatus("ACTIVE");
                    modeOne.setCreatedDateTime(LocalDateTime.now());
                    modeOne.setLastUpdatedDateTime(LocalDateTime.now());
                    modeOne.getResults().get(0).getReadings().get(0).setReadAt(LocalDateTime.now());
                })
                .flatMap(modeOneRepository::save)
                .doOnSuccess(device -> log.info("Successfully saved the Mode One [{}]", device))
                .map(device -> ResponseEntity.ok(CommonResponse.builder()
                        .status("S1000")
                        .statusDescription("Success")
                        .build()));
    }

    // Mode Two related services
    public Mono<ResponseEntity<CommonResponse>> addModeTwo(ModeTwoDto modeTwoDto, String jwt) {
        return userService.getUser(jwtUtils.getUsername(jwt))
                .flatMap(user -> userGroupRoleService.getUserRolesByUserGroup(user.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.SAVE_MODE_TWO)) {
                                return Mono.just(modeTwoDto)
                                        .doOnNext(modeTwoDto1 -> log.info("MOde Two add request received [{}]", modeTwoDto1))
                                        .flatMap(modeTwoDto1 -> modeTwoRepository.findBySessionID(modeTwoDto1.getDefaultConfigurations().getSessionId()))
                                        .flatMap(modeTwo -> Mono.just(modeTwo)
                                                .filter(mOne -> mOne.getResults().stream().anyMatch(i -> Objects.equals(i.getTestId(), modeTwoDto.getResults().get(0).getTestId())))
                                                .flatMap(modeTwo1 ->
                                                        Mono.just(ResponseEntity.ok(CommonResponse.builder()
                                                                .status("E1010")
                                                                .statusDescription("Mode Already exist with taskID")
                                                                .build()))
                                                ).switchIfEmpty(updateSessionTwo(modeTwoDto, modeTwo))
                                        )
                                        .switchIfEmpty(saveModeTwo(modeTwoDto, jwt))
                                        .doOnError(e ->
                                                ResponseEntity.ok(CommonResponse.builder()
                                                        .status("E1000")
                                                        .statusDescription("Failed")
                                                        .build()));
                            } else {
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                        .status("E1220")
                        .statusDescription("Failed")
                        .build())));
    }

    private Mono<ResponseEntity<CommonResponse>> updateSessionTwo(ModeTwoDto modeTwoDto, ModeTwo modeTwo) {
        System.out.println(modeTwo);
        return Mono.just(modeTwo)
                .doOnNext(modeTwo1 -> {
                    modeTwoDto.getResults().get(0).getReadings().get(0).setReadAt(LocalDateTime.now());
                    modeTwo.getResults().add(modeTwoDto.getResults().get(0));
                    modeTwo.setLastUpdatedDateTime(LocalDateTime.now());
                })
                .flatMap(modeTwoRepository::save)
                .doOnSuccess(mOne -> log.info("Successfully updated the Mode Two [{}]", mOne))
                .map(device -> ResponseEntity.ok(CommonResponse.builder()
                        .status("S1000")
                        .statusDescription("Success")
                        .build()));
    }

    private Mono<ResponseEntity<CommonResponse>> saveModeTwo(ModeTwoDto modeTwoDto, String jwt) {
        return Mono.just(modeTwoMapper.modeTwoDtoToModeTwo(modeTwoDto))
                .doOnNext(modeTwo -> {
                    modeTwo.setCreatedBy(jwtUtils.getUsername(jwt));
                    modeTwo.setStatus("ACTIVE");
                    modeTwo.setCreatedDateTime(LocalDateTime.now());
                    modeTwo.setLastUpdatedDateTime(LocalDateTime.now());
                    modeTwo.getResults().get(0).getReadings().get(0).setReadAt(LocalDateTime.now());
                })
                .flatMap(modeTwoRepository::save)
                .doOnSuccess(device -> log.info("Successfully saved the Mode Two [{}]", device))
                .map(device -> ResponseEntity.ok(CommonResponse.builder()
                        .status("S1000")
                        .statusDescription("Success")
                        .build()));
    }

    // Mode Three related services
    public Mono<ResponseEntity<CommonResponse>> addModeThree(ModeThreeDto modeThreeDto, String jwt) {
        return userService.getUser(jwtUtils.getUsername(jwt))
                .flatMap(user -> userGroupRoleService.getUserRolesByUserGroup(user.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.SAVE_MODE_THREE)) {
                                return Mono.just(modeThreeDto)
                                        .doOnNext(modeThreeDto1 -> log.info("MOde three add request received [{}]", modeThreeDto1))
                                        .flatMap(modeThreeDto1 -> modeThreeRepository.findBySessionID(modeThreeDto1.getDefaultConfigurations().getSessionId()))
                                        .flatMap(modeThree -> Mono.just(ResponseEntity.ok(CommonResponse.builder()
                                                        .status("E1010")
                                                        .statusDescription("Mode Already exist with taskID, Session Id")
                                                        .build())
                                                )
                                        )
                                        .switchIfEmpty(saveModeThree(modeThreeDto, jwt))
                                        .doOnError(e ->
                                                ResponseEntity.ok(CommonResponse.builder()
                                                        .status("E1000")
                                                        .statusDescription("Failed")
                                                        .build()));
                            } else {
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                        .status("E1220")
                        .statusDescription("Failed")
                        .build())));

    }

    private Mono<ResponseEntity<CommonResponse>> saveModeThree(ModeThreeDto ModeThreeDto, String jwt) {
        return Mono.just(modeThreeMapper.modeThreeDtoToModeThree(ModeThreeDto))
                .doOnNext(modeThree -> {
                    modeThree.setCreatedBy(jwtUtils.getUsername(jwt));
                    modeThree.setStatus("ACTIVE");
                    modeThree.setCreatedDateTime(LocalDateTime.now());
                    modeThree.setLastUpdatedDateTime(LocalDateTime.now());
                })
                .flatMap(modeThreeRepository::save)
                .doOnSuccess(device -> log.info("Successfully saved the Mode Three [{}]", device))
                .map(device -> ResponseEntity.ok(CommonResponse.builder()
                        .status("S1000")
                        .statusDescription("Success")
                        .build()));
    }

    // Mode Four related services
    public Mono<ResponseEntity<CommonResponse>> addModeFour(ModeFourDto modeFourDto, String jwt) {
        return userService.getUser(jwtUtils.getUsername(jwt))
                .flatMap(user -> userGroupRoleService.getUserRolesByUserGroup(user.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.SAVE_MODE_FOUR)) {
                                return Mono.just(modeFourDto)
                                        .doOnNext(modeFourDto1 -> log.info("MOde four add request received [{}]", modeFourDto1))
                                        .flatMap(modeFourDto1 -> modeFourRepository.findBySessionID(modeFourDto1.getDefaultConfigurations().getSessionId()))
                                        .flatMap(modeFour -> Mono.just(ResponseEntity.ok(CommonResponse.builder()
                                                .status("E1010")
                                                .statusDescription("Mode Already exist with taskID, Session Id")
                                                .build()))

                                        )
                                        .switchIfEmpty(saveModeFour(modeFourDto, jwt))
                                        .doOnError(e ->
                                                ResponseEntity.ok(CommonResponse.builder()
                                                        .status("E1000")
                                                        .statusDescription("Failed")
                                                        .build()));
                            } else {
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                        .status("E1220")
                        .statusDescription("Failed")
                        .build())));
    }

    private Mono<ResponseEntity<CommonResponse>> saveModeFour(ModeFourDto modeFourDto, String jwt) {
        return Mono.just(modeFourMapper.modeFourDtoToModeFour(modeFourDto))
                .doOnNext(modeFour -> {
                    modeFour.setCreatedBy(jwtUtils.getUsername(jwt));
                    modeFour.setStatus("ACTIVE");
                    modeFour.setCreatedDateTime(LocalDateTime.now());
                    modeFour.setLastUpdatedDateTime(LocalDateTime.now());
                })
                .flatMap(modeFourRepository::save)
                .doOnSuccess(device -> log.info("Successfully saved the Mode Four [{}]", device))
                .map(device -> ResponseEntity.ok(CommonResponse.builder()
                        .status("S1000")
                        .statusDescription("Success")
                        .build()));
    }

    // Mode Five related services
    public Mono<ResponseEntity<CommonResponse>> addModeFive(ModeFiveDto modeFiveDto, String jwt) {
        return userService.getUser(jwtUtils.getUsername(jwt))
                .flatMap(user -> userGroupRoleService.getUserRolesByUserGroup(user.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.SAVE_MODE_FIVE)) {
                                return Mono.just(modeFiveDto)
                                        .doOnNext(modeFiveDto1 -> log.info("MOde five add request received [{}]", modeFiveDto1))
                                        .flatMap(modeFiveDto1 -> modeFiveRepository.findBySessionID(modeFiveDto1.getDefaultConfigurations().getSessionId()))
                                        .flatMap(modeFive -> Mono.just(ResponseEntity.ok(CommonResponse.builder()
                                                .status("E1010")
                                                .statusDescription("Mode Already exist with taskID, Session Id")
                                                .build()))

                                        )
                                        .switchIfEmpty(saveModeFive(modeFiveDto, jwt))
                                        .doOnError(e ->
                                                ResponseEntity.ok(CommonResponse.builder()
                                                        .status("E1000")
                                                        .statusDescription("Failed")
                                                        .build()));
                            } else {
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                        .status("E1220")
                        .statusDescription("Failed")
                        .build())));
    }

    private Mono<ResponseEntity<CommonResponse>> saveModeFive(ModeFiveDto modeFiveDto, String jwt) {
        return Mono.just(modeFiveMapper.modeFiveDtoToModeFive(modeFiveDto))
                .doOnNext(modeFive -> {
                    modeFive.setCreatedBy(jwtUtils.getUsername(jwt));
                    modeFive.setStatus("ACTIVE");
                    modeFive.setCreatedDateTime(LocalDateTime.now());
                    modeFive.setLastUpdatedDateTime(LocalDateTime.now());
                })
                .flatMap(modeFiveRepository::save)
                .doOnSuccess(device -> log.info("Successfully saved the Mode Five [{}]", device))
                .map(device -> ResponseEntity.ok(CommonResponse.builder()
                        .status("S1000")
                        .statusDescription("Success")
                        .build()));
    }

    // Mode Six related services
    public Mono<ResponseEntity<CommonResponse>> addModeSix(ModeSixDto modeSixDto, String jwt) {
        return userService.getUser(jwtUtils.getUsername(jwt))
                .flatMap(user -> userGroupRoleService.getUserRolesByUserGroup(user.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.SAVE_MODE_SIX)) {
                                return Mono.just(modeSixDto)
                                        .doOnNext(modeSixDto1 -> log.info("MOde six add request received [{}]", modeSixDto1))
                                        .flatMap(modeSixDto1 -> modeSixRepository.findBySessionID(modeSixDto1.getDefaultConfigurations().getSessionId()))
                                        .flatMap(modeSix -> Mono.just(ResponseEntity.ok(CommonResponse.builder()
                                                .status("E1010")
                                                .statusDescription("Mode Already exist with taskID, Session Id")
                                                .build()))

                                        )
                                        .switchIfEmpty(saveModeSix(modeSixDto, jwt))
                                        .doOnError(e ->
                                                ResponseEntity.ok(CommonResponse.builder()
                                                        .status("E1000")
                                                        .statusDescription("Failed")
                                                        .build()));
                            } else {
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
                        .status("E1200")
                        .statusDescription("Failed")
                        .build())));
    }

    private Mono<ResponseEntity<CommonResponse>> saveModeSix(ModeSixDto modeSixDto, String jwt) {
        return Mono.just(modeSixMapper.modeSixDtoToModeSix(modeSixDto))
                .doOnNext(modeSix -> {
                    modeSix.setCreatedBy(jwtUtils.getUsername(jwt));
                    modeSix.setStatus("ACTIVE");
                    modeSix.setCreatedDateTime(LocalDateTime.now());
                    modeSix.setLastUpdatedDateTime(LocalDateTime.now());
                })
                .flatMap(modeSixRepository::save)
                .doOnSuccess(device -> log.info("Successfully saved the Mode Six [{}]", device))
                .map(device -> ResponseEntity.ok(CommonResponse.builder()
                        .status("S1000")
                        .statusDescription("Success")
                        .build()));
    }

    public Mono<ResponseEntity<ModeOnesResponse>> getAllModeOne(String pageNo, SessionSearchRequest request, String jwt) {
        return userService.getUser(jwtUtils.getUsername(jwt))
                .flatMap(user -> userGroupRoleService.getUserRolesByUserGroup(user.getGroup())
                        .flatMap(userRoles -> userService.getUsersByAdmin(user.getUsername())
                                .filter(strings -> !strings.isEmpty())
                                .flatMap(strings -> {
                                    if (userRoles.contains(UserRoles.GET_MODE_ONE)) {
                                        if (getFilters(strings, pageNo, request).isEmpty()) {
                                            return Mono.just(ResponseEntity.ok(ModeOnesResponse.builder()
                                                    .status("S1000")
                                                    .statusDescription("Success")
                                                    .sessions(new ArrayList<>())
                                                    .build()));
                                        } else {
                                            return modeOneRepository.findByFilters(getFilters(strings, pageNo, request))
                                                    .flatMap(modeOne -> {
                                                        log.info("Mode one found with id [{}]", modeOne.getDefaultConfigurations().getSessionId());
                                                        return Mono.just(modeOneMapper.modeOneToModeOneDto(modeOne));
                                                    })
                                                    .collectList()
                                                    .flatMap(modeOneDtos -> Mono.just(ResponseEntity.ok(ModeOnesResponse.builder()
                                                            .status("S1000")
                                                            .statusDescription("Success")
                                                            .sessions(modeOneDtos)
                                                            .build())));
                                        }
                                    } else {
                                        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ModeOnesResponse.builder()
                                                .status("E1200")
                                                .statusDescription("You are not authorized to use this service").build()));
                                    }
                                })
                                .switchIfEmpty(Mono.just(ResponseEntity.ok(ModeOnesResponse.builder()
                                        .status("S1000")
                                        .statusDescription("Success")
                                        .sessions(new ArrayList<>())
                                        .build()))))
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ModeOnesResponse.builder()
                        .status("E1220")
                        .statusDescription("Failed")
                        .build())));
    }

    public Mono<ResponseEntity<ModeTwosResponse>> getAllModeTwos(String pageNo, String jwt) {
        return userService.getUser(jwtUtils.getUsername(jwt))
                .flatMap(user -> userGroupRoleService.getUserRolesByUserGroup(user.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.GET_MODE_TWO)) {
                                return modeTwoRepository.findAll()
                                        .flatMap(modeTwo -> {
                                            log.info("Mode two found with id [{}]", modeTwo.getDefaultConfigurations().getSessionId());
                                            return Mono.just(modeTwoMapper.modeTwoToModeTwoDto(modeTwo));
                                        })
                                        .collectList()
                                        .flatMap(modeTwoDtos -> Mono.just(ResponseEntity.ok(ModeTwosResponse.builder()
                                                .status("S1000")
                                                .statusDescription("Success")
                                                .sessions(modeTwoDtos)
                                                .build())));
                            } else {
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ModeTwosResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ModeTwosResponse.builder()
                        .status("E1200")
                        .statusDescription("Failed")
                        .build())));
    }

    public Mono<ResponseEntity<ModeThreesResponse>> getAllModeThrees(String pageNo, String jwt) {
        return userService.getUser(jwtUtils.getUsername(jwt))
                .flatMap(user -> userGroupRoleService.getUserRolesByUserGroup(user.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.GET_MODE_THREE)) {
                                return modeThreeRepository.findAll()
                                        .flatMap(modeThree -> {
                                            log.info("Mode three found with id [{}]", modeThree.getDefaultConfigurations().getSessionId());
                                            return Mono.just(modeThreeMapper.modeThreeToModeThreeDto(modeThree));
                                        })
                                        .collectList()
                                        .flatMap(modeThreeDtos -> Mono.just(ResponseEntity.ok(ModeThreesResponse.builder()
                                                .status("S1000")
                                                .statusDescription("Success")
                                                .sessions(modeThreeDtos)
                                                .build())));
                            } else {
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ModeThreesResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ModeThreesResponse.builder()
                        .status("E1220")
                        .statusDescription("Failed")
                        .build())));
    }

    public Mono<ResponseEntity<ModeFoursResponse>> getAllModeFour(String pageNo, String jwt) {
        return userService.getUser(jwtUtils.getUsername(jwt))
                .flatMap(user -> userGroupRoleService.getUserRolesByUserGroup(user.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.GET_MODE_FOUR)) {
                                return modeFourRepository.findAll()
                                        .flatMap(modeFour -> {
                                            log.info("Mode four found with id [{}]", modeFour.getDefaultConfigurations().getSessionId());
                                            return Mono.just(modeFourMapper.modeFourToModeFourDto(modeFour));
                                        })
                                        .collectList()
                                        .flatMap(modeFourDtos -> Mono.just(ResponseEntity.ok(ModeFoursResponse.builder()
                                                .status("S1000")
                                                .statusDescription("Success")
                                                .sessions(modeFourDtos)
                                                .build())));
                            } else {
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ModeFoursResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ModeFoursResponse.builder()
                        .status("E1220")
                        .statusDescription("Failed")
                        .build())));
    }

    public Mono<ResponseEntity<ModeFiveResponse>> getAllModeFive(String pageNo, String jwt) {
        return userService.getUser(jwtUtils.getUsername(jwt))
                .flatMap(user -> userGroupRoleService.getUserRolesByUserGroup(user.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.GET_MODE_FIVE)) {
                                return modeFiveRepository.findAll()
                                        .flatMap(modeFive -> {
                                            log.info("Mode five found with id [{}]", modeFive.getDefaultConfigurations().getSessionId());
                                            return Mono.just(modeFiveMapper.modeFiveToModeFiveDto(modeFive));
                                        })
                                        .collectList()
                                        .flatMap(modeFiveDtos -> Mono.just(ResponseEntity.ok(ModeFiveResponse.builder()
                                                .status("S1000")
                                                .statusDescription("Success")
                                                .sessions(modeFiveDtos)
                                                .build())));
                            } else {
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ModeFiveResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ModeFiveResponse.builder()
                        .status("E1220")
                        .statusDescription("Failed")
                        .build())));
    }

    public Mono<ResponseEntity<ModeSixResponse>> getAllModeSix(String pageNo, String jwt) {
        return userService.getUser(jwtUtils.getUsername(jwt))
                .flatMap(user -> userGroupRoleService.getUserRolesByUserGroup(user.getGroup())
                        .flatMap(userRoles -> {
                            if (userRoles.contains(UserRoles.GET_MODE_SIX)) {
                                return modeSixRepository.findAll()
                                        .flatMap(modeSix -> {
                                            log.info("Mode six found with id [{}]", modeSix.getDefaultConfigurations().getSessionId());
                                            return Mono.just(modeSixMapper.modeSixToModeSixDto(modeSix));
                                        })
                                        .collectList()
                                        .flatMap(modeFourDtos -> Mono.just(ResponseEntity.ok(ModeSixResponse.builder()
                                                .status("S1000")
                                                .statusDescription("Success")
                                                .sessions(modeFourDtos)
                                                .build())));
                            } else {
                                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ModeSixResponse.builder()
                                        .status("E1200")
                                        .statusDescription("You are not authorized to use this service").build()));
                            }
                        })
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ModeSixResponse.builder()
                        .status("E1220")
                        .statusDescription("Failed")
                        .build())));
    }

    private String getFilters(List<String> users, String pageNo, SessionSearchRequest request) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{'$skip': " + Integer.valueOf(pageNo) * 20 + " }");

        if (!stringBuilder.toString().isEmpty()) {
            stringBuilder.append(", ");
        }

        if (request.getFilterType() != null && !request.getFilterValue().isEmpty()) {
            if (request.getFilterType() != FilterType.CREATED_BY) {
                if (users.size() > 0) {
                    stringBuilder.append("{'$match':{")
                            .append("'created-by': { '$in' : ")
                            .append(users)
                            .append("}");
                }

                switch (request.getFilterType()) {
                    case BATCH_NO -> stringBuilder.append("'default-configurations.batch-no' : { $regex : '.*")
                            .append(request.getFilterValue())
                            .append(".*'}");
                    case SESSION_ID -> stringBuilder.append("'default-configurations.session-id': { $regex : '.*")
                            .append(request.getFilterValue())
                            .append(".*'}");
                    case OPERATOR_ID -> stringBuilder.append("'default-configurations.operator-id': { $regex : '.*")
                            .append(request.getFilterValue())
                            .append(".*'}");
                    case CUSTOMER_NAME -> stringBuilder.append("'default-configurations.customer-name': { $regex : '.*")
                            .append(request.getFilterValue())
                            .append(".*'}");
                }

            } else if (checkUserRegexMatchWithUsers(users, request.getFilterValue())) {
                stringBuilder.append("{'$match':{")
                        .append("'created-by': { $regex : '.*")
                        .append(request.getFilterValue())
                        .append(".*'}");
            } else {
                if (users.size() > 0) {
                    stringBuilder.append("{'$match':{")
                            .append("'created-by': { '$in' : ")
                            .append(users)
                            .append("}");
                }
            }
        } else {
            if (users.size() > 0) {
                stringBuilder.append("{'$match':{")
                        .append("'created-by': { '$in' : ")
                        .append(users)
                        .append("}");
            }
        }

        if (!stringBuilder.toString().isEmpty()) {
            stringBuilder.append("}} , {'$limit': 20 }");
        } else {
            stringBuilder.append(" {'$limit': 20}");
        }

        log.info("AAAAAAAAAAAA :  {}", stringBuilder.toString());
        return stringBuilder.toString();
    }

    private boolean checkUserRegexMatchWithUsers(List<String> users, String pattern) {
        AtomicBoolean contain = new AtomicBoolean(false);
        users.forEach(s -> {
            if (s.contains(pattern)) {
                contain.set(true);
            }
        });
        return contain.get();
    }
}