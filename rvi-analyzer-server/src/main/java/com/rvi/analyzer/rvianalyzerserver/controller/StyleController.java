package com.rvi.analyzer.rvianalyzerserver.controller;

import com.rvi.analyzer.rvianalyzerserver.domain.CommonResponse;
import com.rvi.analyzer.rvianalyzerserver.domain.NewStyleResponse;
import com.rvi.analyzer.rvianalyzerserver.domain.StyleResponse;
import com.rvi.analyzer.rvianalyzerserver.domain.UpdateStyle;
import com.rvi.analyzer.rvianalyzerserver.dto.StyleDto;
import com.rvi.analyzer.rvianalyzerserver.service.StyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class StyleController {

    final private StyleService styleService;

    @PostMapping(path = "/register/style")
    public Mono<NewStyleResponse> addStyle(@RequestBody StyleDto styleDto, @RequestHeader("Authorization") String auth) {
        return styleService.addStyle(styleDto, auth);
    }

    @PostMapping(path = "/allocate/style/admin")
    public Mono<ResponseEntity<CommonResponse>> allocateAdmin(@RequestBody UpdateStyle updateStyle, @RequestHeader("Authorization") String auth) {
        return styleService.allocateAdmin(updateStyle, auth);
    }

    @PostMapping(path = "/allocate/style")
    public Mono<ResponseEntity<CommonResponse>> allocateStyle(@RequestBody UpdateStyle updateStyle, @RequestHeader("Authorization") String auth) {
        return styleService.updateStyle(updateStyle, auth);
    }

    @GetMapping(path = "/rvi/analyzer/v1/styles")
    public Mono<ResponseEntity<StyleResponse>> getStyles(@RequestHeader("Authorization") String auth) {
        return styleService.getStyles(auth);
    }

    @PostMapping(path = "/delete/style/{name}")
    public Mono<CommonResponse> deleteStyle(@RequestHeader("Authorization") String auth, @PathVariable String name) {
        return styleService.deleteStyleByName(auth, name);
    }
}
