package com.study.basecode.domain.store.dto;

import com.study.basecode.domain.store.entity.Store;
import lombok.Getter;

@Getter
public class StoreResponseDto {
    private Long id;

    private String fullLocationAddress;
    private String fullRoadNameAddress;
    private Long roadNamePostalCode;
    private String businessName;
    private String businessType;
    private Long longitude;
    private Long latitude;
    private String sanitationBusinessType;
    private String menu;

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.fullLocationAddress = store.getFullLocationAddress();
        this.fullRoadNameAddress = store.getFullRoadNameAddress();
        this.roadNamePostalCode = store.getRoadNamePostalCode();
        this.businessName = store.getBusinessName();
        this.businessType = store.getBusinessType();
        this.longitude = store.getLongitude();
        this.latitude = store.getLatitude();
        this.sanitationBusinessType = store.getSanitationBusinessType();
        this.menu = store.getMenu();
    }
}
