package com.study.basecode.domain.store.service;

import com.study.basecode.domain.store.dto.CustomPage;
import com.study.basecode.domain.store.dto.StoreResponseDto;
import com.study.basecode.domain.store.entity.Store;
import com.study.basecode.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final RedisTemplate<String, CustomPage> redisTemplate;

    private static final String STORE_CACHE_KEY = "store:";

    public Page<StoreResponseDto> findAllStore(Pageable pageable) {
        Page<Store> allStore = storeRepository.findAll(pageable);
        return allStore.map(StoreResponseDto::new);
    }

    public Page<StoreResponseDto> findOneStore(Pageable pageable, String name) {
        Page<Store> findStore = storeRepository.findAllByName(pageable, name);
        return findStore.map(StoreResponseDto::new);
    }

    public CustomPage findOneRedisStore(Pageable pageable, String name) {
        String redisKey = STORE_CACHE_KEY + name + pageable.getPageNumber();

        // Redis에서 CustomPage를 가져오기
        CustomPage cachedStore = redisTemplate.opsForValue().get(redisKey);

        // 캐시가 존재할 경우 반환
        if (cachedStore != null) {
            return cachedStore;
        }

        // 데이터베이스에서 데이터를 가져오기
        Page<Store> findStore = storeRepository.findAllByName(pageable, name);
        CustomPage customPage = new CustomPage(findStore);

        // Redis에 CustomPage 저장
        redisTemplate.opsForValue().set(redisKey, customPage, 30, TimeUnit.MINUTES);

        return customPage;
    }
}
