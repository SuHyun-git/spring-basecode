package com.study.basecode.domain.store.controller;

import com.study.basecode.domain.store.dto.StoreLikeResponseDto;
import com.study.basecode.domain.store.dto.StoreResponseDto;
import com.study.basecode.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Page<StoreResponseDto> storeResponseDtoList = storeService.findAllStore(pageable);
        return ResponseEntity.ok().body(storeResponseDtoList);
    }

    // 이름으로 음식점 검색
    @GetMapping("/searchn")
    public ResponseEntity<Page<StoreResponseDto>> findStoreWithName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String name
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StoreResponseDto> storeResponseDtoList = storeService.findStoreWithName(pageable, name);
        return ResponseEntity.ok().body(storeResponseDtoList);
    }

    @PatchMapping("/{storeId}/like/{userId}")
    public ResponseEntity<StoreLikeResponseDto> storeLike (
            @PathVariable("storeId") Long storeId,
            @PathVariable("userId") Long userId
    ) {
        StoreLikeResponseDto storeLikeResponseDto = storeService.storeLike(storeId, userId);
        return ResponseEntity.ok().body(storeLikeResponseDto);
    }
}
