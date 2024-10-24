package com.study.basecode.domain.memo.controller;


import com.study.basecode.domain.auth.dto.AuthUser;
import com.study.basecode.domain.memo.dto.MemoRequsetDto;
import com.study.basecode.domain.memo.dto.MemoResponseDto;
import com.study.basecode.domain.memo.service.MemoService;
import com.study.basecode.security.Auth;
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
    public ResponseEntity<MemoResponseDto> createMemo(@Auth AuthUser authUser, @RequestBody MemoRequsetDto memoRequsetDto) {
        MemoResponseDto memoResponseDto = memoService.createMemo(authUser, memoRequsetDto);
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

}
