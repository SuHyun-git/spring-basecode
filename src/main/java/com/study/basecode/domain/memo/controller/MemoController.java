package com.study.basecode.domain.memo.controller;


import com.study.basecode.domain.memo.dto.MemoOptimisticResponseDto;
import com.study.basecode.domain.memo.dto.MemoRequsetDto;
import com.study.basecode.domain.memo.dto.MemoResponseDto;
import com.study.basecode.domain.memo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memos")
public class MemoController {

    private final MemoService memoService;

    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequsetDto memoRequsetDto) {
        MemoResponseDto memoResponseDto = memoService.createMemo(memoRequsetDto);
        return ResponseEntity.status(HttpStatus.OK).body(memoResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<MemoResponseDto>> getMemo() {
        List<MemoResponseDto> memoResponseDtoList = memoService.getMemo();
        return ResponseEntity.status(HttpStatus.OK).body(memoResponseDtoList);
    }

    @PatchMapping("/{memoId}")
    public ResponseEntity<MemoResponseDto> patchMemo(@PathVariable("memoId") Long memoId, @RequestBody MemoRequsetDto memoRequsetDto) {
        MemoResponseDto memoResponseDto = memoService.patchMemo(memoId, memoRequsetDto);
        return ResponseEntity.status(HttpStatus.OK).body(memoResponseDto);
    }

    @DeleteMapping("/{memoId}")
    public void deleteMemo(@PathVariable("memoId") Long memoId) {
        memoService.deleteMemo(memoId);
    }

    // 락을 사용하지 않았을 때
    @PatchMapping("/{memoId}/like/nolock")
    public ResponseEntity<MemoResponseDto> likeNoLock(@PathVariable("memoId") Long memoId) {
        MemoResponseDto memoResponseDto = memoService.likeNoLock(memoId);
        return ResponseEntity.status(HttpStatus.OK).body(memoResponseDto);
    }

    // 비관적 락
    @PatchMapping("/{memoId}/like/pessimistic")
    public ResponseEntity<MemoResponseDto> likePessimistic(@PathVariable("memoId") Long memoId) {
        MemoResponseDto memoResponseDto = memoService.likePessimistic(memoId);
        return ResponseEntity.status(HttpStatus.OK).body(memoResponseDto);
    }

    // 낙관적 락
    @PatchMapping("/{memoId}/like/optimistic")
    public ResponseEntity<MemoOptimisticResponseDto> likeOptimistic(@PathVariable("memoId") Long memoId) {
        MemoOptimisticResponseDto memoResponseDto = memoService.likeOptimistic(memoId);
        return ResponseEntity.status(HttpStatus.OK).body(memoResponseDto);
    }

    // redis 분산락 사용
    @PatchMapping("/{memoId}/like/redis")
    public ResponseEntity<MemoResponseDto> likeRedis(@PathVariable("memoId") Long memoId) {
        MemoResponseDto memoResponseDto = memoService.likeRedis(memoId);
        return ResponseEntity.status(HttpStatus.OK).body(memoResponseDto);
    }

}
