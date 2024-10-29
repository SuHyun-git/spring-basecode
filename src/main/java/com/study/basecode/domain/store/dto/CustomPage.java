package com.study.basecode.domain.store.dto;

import com.study.basecode.domain.store.entity.Store;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class CustomPage {
    private List<Store> content;
    private int totalPages;
    private long totalElements;

    public CustomPage(Page<Store> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }

}