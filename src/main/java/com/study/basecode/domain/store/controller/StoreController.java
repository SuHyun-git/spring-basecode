package com.study.basecode.domain.store.controller;

import com.study.basecode.domain.store.dto.CustomPage;
import com.study.basecode.domain.store.dto.StoreResponseDto;
import com.study.basecode.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping()
    public ResponseEntity<Page<StoreResponseDto>> findAllStore(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StoreResponseDto> storeResponseDtoList =  storeService.findAllStore(pageable);
        return ResponseEntity.ok().body(storeResponseDtoList);
    }

    @GetMapping("/searchn")
    public ResponseEntity<Page<StoreResponseDto>> findOneStore(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String name
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StoreResponseDto> storeResponseDtoList =  storeService.findOneStore(pageable, name);
        return ResponseEntity.ok().body(storeResponseDtoList);
    }

    @GetMapping("/search")
    public ResponseEntity<CustomPage> findOneStoreRedis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String name
    ) {
        Pageable pageable = PageRequest.of(page, size);
        CustomPage storeResponseDtoList = storeService.findOneRedisStore(pageable, name);
        return ResponseEntity.ok().body(storeResponseDtoList);
    }

}
