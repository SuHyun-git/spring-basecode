package com.study.basecode.domain.store.service;

import com.study.basecode.domain.store.dto.StoreResponseDto;
import com.study.basecode.domain.store.entity.Store;
import com.study.basecode.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public Page<StoreResponseDto> findAllStore(Pageable pageable) {
        Page<Store> allStore = storeRepository.findAll(pageable);
        return allStore.map(StoreResponseDto::new);
    }

    public Page<StoreResponseDto> findOneStore(Pageable pageable, String name) {
        Page<Store> findStore = storeRepository.findAllByName(pageable, name);
        return findStore.map(StoreResponseDto::new);
    }
}
