package com.study.basecode.domain.memo.service;

import com.study.basecode.domain.memo.dto.MemoRequsetDto;
import com.study.basecode.domain.memo.dto.MemoResponseDto;
import com.study.basecode.domain.memo.entity.Memo;
import com.study.basecode.domain.memo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    // RedisTemplate을 사용하여 Redis와 통신
    private final RedisTemplate<String, Memo> redisTemplate;

    private static final String MEMO_CACHE_KEY = "memo:";

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


    // Memo 데이터를 가져오는 메소드
    public MemoResponseDto getOneMemoV1(Long memoId) {
        String redisKey = MEMO_CACHE_KEY + memoId;

        // 1. 먼저 Redis에서 데이터를 조회하기
        Memo cashedMemo = redisTemplate.opsForValue().get(redisKey);

        if (cashedMemo != null) {
            // 2. Redis에서 데이터가 존재하면 캐시에서 반환하기 (Cache Hit)
            System.out.println("Cache Hit V1 - Returning memo from Redis");
            return new MemoResponseDto(cashedMemo);
        }

        // 3. Redis에 데이터가 없다면 DB에서 데이터 조히하기 (Cache Miss)
        System.out.println("Cache Miss V1 - Fetching memo from DB");
        Memo memoFromDb = memoRepository.findById(memoId).orElseThrow(() -> new IllegalArgumentException("메모를 찾을 수 없습니다."));

        // 4. DB에서 조회한 데이터를 Redis에 저장 (캐시 유지 시간 설정 가능)
        redisTemplate.opsForValue().set(redisKey, memoFromDb, 30, TimeUnit.MINUTES); // 30분 동안 유지하기

        // 5. DB에서 가져온 데이터 반환
        return new MemoResponseDto(memoFromDb);
    }

//    public MemoResponseDto getOneMemoV2(Long memoId) {
//    }
}
