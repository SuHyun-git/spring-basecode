package com.study.basecode.domain.store.service;


import com.study.basecode.domain.store.dto.StoreLikeResponseDto;
import com.study.basecode.domain.store.dto.StoreResponseDto;
import com.study.basecode.domain.store.entity.Store;
import com.study.basecode.domain.store.entity.StoreLike;
import com.study.basecode.domain.store.repository.StoreLikeRepository;
import com.study.basecode.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreLikeRepository storeLikeRepository;

    private static final String STORE_CACHE_KEY = "store:";

    public Page<StoreResponseDto> findAllStore(Pageable pageable) {
        Page<Store> allStore = storeRepository.findAll(pageable);
        return allStore.map(StoreResponseDto::new);
    }

    public Page<StoreResponseDto> findStoreWithName(Pageable pageable, String name) {
        Page<Store> findStore = storeRepository.findAllByName(pageable, name);
        return findStore.map(StoreResponseDto::new);
    }

    @Transactional
    public StoreLikeResponseDto storeLike(Long storeId, Long userId) {
        Store findStore = storeRepository.findById(storeId).orElseThrow();

        StoreLike storeLike = storeLikeRepository.findByStoreIdAndUserId(findStore.getId(), userId);

        if (storeLike == null) {
            StoreLike newStoreLike = new StoreLike(userId, findStore);
            storeLike = storeLikeRepository.save(newStoreLike);

        } else {
            storeLike.changeLike();
            storeLike = storeLikeRepository.save(storeLike);
        }

        return new StoreLikeResponseDto(storeLike);
    }
}
