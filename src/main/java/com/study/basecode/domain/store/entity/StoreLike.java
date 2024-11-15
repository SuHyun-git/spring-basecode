package com.study.basecode.domain.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "store_like", indexes = @Index(name = "idx_store_like", columnList = "user_id"))
public class StoreLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private Boolean storeLike = true;

    public StoreLike(Long userId, Store store) {
        this.userId = userId;
        this.store = store;
    }

    public void changeLike() {
        this.storeLike = !this.storeLike;
    }
}
