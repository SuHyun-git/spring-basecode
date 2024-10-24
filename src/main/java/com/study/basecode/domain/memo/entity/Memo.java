package com.study.basecode.domain.memo.entity;

import com.study.basecode.domain.memo.dto.MemoRequsetDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long id;

    private String name;

    private String title;

    private String contents;

    public Memo(String name, MemoRequsetDto memoRequsetDto) {
        this.name = name;
        title = memoRequsetDto.getTitle();
        contents = memoRequsetDto.getContents();
    }

    public Memo patchMemo(MemoRequsetDto memoRequsetDto) {
        title = memoRequsetDto.getTitle();
        contents = memoRequsetDto.getContents();
        return this;
    }
}
