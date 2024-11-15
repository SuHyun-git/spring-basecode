package com.study.basecode.domain.store.repository;

import com.study.basecode.domain.store.entity.StoreLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreLikeRepository extends JpaRepository<StoreLike, Long> {
    StoreLike findByStoreIdAndUserId(Long storeId, Long userId);
}
