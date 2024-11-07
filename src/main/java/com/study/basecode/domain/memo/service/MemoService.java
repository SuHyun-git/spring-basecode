package com.study.basecode.domain.memo.service;

import com.study.basecode.domain.memo.dto.MemoOptimisticResponseDto;
import com.study.basecode.domain.memo.dto.MemoRequsetDto;
import com.study.basecode.domain.memo.dto.MemoResponseDto;
import com.study.basecode.domain.memo.entity.Memo;
import com.study.basecode.domain.memo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.hibernate.annotations.OptimisticLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    // redisson
    private final String REDISSON_LOCK_PREFIX = "LOCK:";
    private final RedissonClient redissonClient;

    public MemoResponseDto createMemo(MemoRequsetDto memoRequsetDto) {
        Memo memo = new Memo(memoRequsetDto);
        return new MemoResponseDto(memoRepository.save(memo));
    }

    public List<MemoResponseDto> getMemo() {
        return memoRepository.findAll().stream().map(MemoResponseDto::new).toList();
    }

    @Transactional
    public MemoResponseDto patchMemo(Long memoId, MemoRequsetDto memoRequsetDto) {
        Memo findMemo = memoRepository.findById(memoId).orElseThrow();
        return new MemoResponseDto(findMemo.patchMemo(memoRequsetDto));
    }

    public void deleteMemo(Long memoId) {
        memoRepository.delete(memoRepository.findById(memoId).orElseThrow());
    }

    // 락이 없는 경우
    @Transactional
    public MemoResponseDto likeNoLock(Long memoId) {
        Memo findMemo = memoRepository.findById(memoId).orElseThrow(() -> new IllegalArgumentException("메모를 찾을 수 없습니다."));

        findMemo.increaseLike();

        return new MemoResponseDto(findMemo);
    }

    // 비관적 락 적용
    @Transactional
    public MemoResponseDto likePessimistic(Long memoId) {
        Memo findMemo = memoRepository.findByIdPessimistic(memoId).orElseThrow(() -> new IllegalArgumentException("메모를 찾을 수 없습니다."));

        findMemo.increaseLike();

        return new MemoResponseDto(findMemo);
    }

    // 낙관적 락 적용
    public MemoOptimisticResponseDto likeOptimistic(Long memoId) {
        boolean success = false;
        int failureCount = 0; // 실패 횟수 기록
        Memo findMemo = null;

        while (!success) {
            try {
                // 1. 로직 수행: 좋아요 증가
                findMemo = memoRepository.findById(memoId).orElseThrow(() -> new IllegalArgumentException("메모를 찾을 수 없습니다."));
                findMemo.increaseLike();
                memoRepository.save(findMemo);

                // 2. 성공 처리
                success = true;
            } catch (ObjectOptimisticLockingFailureException | StaleObjectStateException ee) {
                failureCount++;
            }
        }

        return new MemoOptimisticResponseDto(findMemo, failureCount);
    }

    // redisson 분산락 적용
    public MemoResponseDto likeRedis(Long memoId) {
        String key = REDISSON_LOCK_PREFIX + memoId;
        RLock rLock = redissonClient.getLock(key);
        Memo findMemo = null;

        try{
            boolean available = rLock.tryLock(10, 2, TimeUnit.SECONDS);
            if (!available) {
                log.info("Lock 획득 실패 = " + key);
                throw new RuntimeException("Lock 획득 실패: 다른 프로세스에서 사용 중입니다.");
            }

            // 로직 실행
            findMemo = updateMemoLikes(memoId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 상태 복원
            log.error("락 획득 중 인터럽트 발생", e);
            throw new RuntimeException("락 획득 중 인터럽트 발생", e);
        } finally {
            if (rLock.isHeldByCurrentThread()) { // 현재 스레드가 락을 가지고 있는지 확인
                log.info("락 해제: " + key);
                rLock.unlock();
            } else {
                log.warn("이미 락이 해제되었습니다: " + key);
            }
        }

        return new MemoResponseDto(findMemo);
    }

    @Transactional
    public Memo updateMemoLikes(Long memoId) {
        Memo findMemo = memoRepository.findById(memoId)
                .orElseThrow(() -> new IllegalArgumentException("메모를 찾을 수 없습니다."));
        findMemo.increaseLike();
        memoRepository.save(findMemo);
        return findMemo;
    }
}
