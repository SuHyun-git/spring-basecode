package com.study.basecode.domain.memo.service;

import com.study.basecode.domain.memo.dto.MemoRequsetDto;
import com.study.basecode.domain.memo.dto.MemoResponseDto;
import com.study.basecode.domain.memo.entity.Memo;
import com.study.basecode.domain.memo.repository.MemoRepository;
import com.study.basecode.domain.user.entity.User;
import com.study.basecode.domain.user.repository.UserRepository;
import com.study.basecode.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    public MemoResponseDto createMemo(AuthUser authUser, MemoRequsetDto memoRequsetDto) {
        User findUser = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Memo memo = new Memo(findUser.getName(), memoRequsetDto);
        return new MemoResponseDto(memoRepository.save(memo));
    }

    public List<MemoResponseDto> getMemo() {
        return memoRepository.findAll().stream().map(MemoResponseDto::new).toList();
    }

    @Transactional
    public MemoResponseDto patchMemo(AuthUser authUser, Long memoId, MemoRequsetDto memoRequsetDto) {
        User findUser = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Memo findMemo = memoRepository.findById(memoId).orElseThrow();
        return new MemoResponseDto(findMemo.patchMemo(findUser.getName(), memoRequsetDto));
    }

    public void deleteMemo(Long memoId) {
        memoRepository.delete(memoRepository.findById(memoId).orElseThrow());
    }
}
