package com.study.basecode.domain.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
