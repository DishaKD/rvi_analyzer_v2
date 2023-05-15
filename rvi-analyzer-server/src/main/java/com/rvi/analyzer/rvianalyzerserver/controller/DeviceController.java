package com.rvi.analyzer.rvianalyzerserver.controller;


import com.rvi.analyzer.rvianalyzerserver.domain.*;
import com.rvi.analyzer.rvianalyzerserver.dto.DeviceDto;
import com.rvi.analyzer.rvianalyzerserver.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class DeviceController {

    final private DeviceService deviceService;

    @PostMapping(path = "/rvi/analyzer/v1/device/add")
    public Mono<ResponseEntity<CommonResponse>> addDevice(@RequestBody DeviceDto deviceDto, @RequestHeader("Authorization") String auth) {
        return deviceService.addDevice(deviceDto, auth);
    }

    @PostMapping("/rvi/analyzer/v1/device/validate/mac")
    public Mono<ResponseEntity<CommonResponse>> validateDevice(@RequestBody DeviceValidateRequestByMac dvr, @RequestHeader("Authorization") String auth) {
        return deviceService.validateDeviceByMac(dvr, auth);
    }

    @PostMapping("/rvi/analyzer/v1/device/update")
    public Mono<ResponseEntity<CommonResponse>> updateDevice(@RequestBody UpdateDeviceRequestByName dvr, @RequestHeader("Authorization") String auth) {
        return deviceService.updateDevice(dvr, auth);
    }

    @GetMapping("/rvi/analyzer/v1/device/search/{page}/{status}/{name}")
    public Mono<ResponseEntity<DeviceListResponse>> getDevices(@PathVariable String page,
                                                               @PathVariable String status,
                                                               @PathVariable String name,
                                                               @RequestHeader("Authorization") String auth) {
        return deviceService.getDevices(page, status, name, auth);
    }

    @GetMapping("/rvi/analyzer/v1/get/dashboard")
    public Mono<ResponseEntity<DashboardResponse>> getDashboardData(@RequestHeader("Authorization") String auth) {
        return deviceService.getDashboard(auth);
    }
}
