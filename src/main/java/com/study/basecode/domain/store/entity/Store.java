package com.study.basecode.domain.store.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
// @Table(name = "store", indexes = @Index(name = "idx_business_name", columnList = "businessName"))
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
