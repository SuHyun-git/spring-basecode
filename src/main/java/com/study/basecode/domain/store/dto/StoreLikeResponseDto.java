package com.study.basecode.domain.store.dto;

import com.study.basecode.domain.store.entity.StoreLike;
import lombok.Getter;

@Getter
public class StoreLikeResponseDto {
    private String businessName;
    private boolean storeLike;

    public StoreLikeResponseDto(StoreLike storeLike) {
        this.businessName = storeLike.getStore().getBusinessName();
        this.storeLike = storeLike.getStoreLike();
    }
}
