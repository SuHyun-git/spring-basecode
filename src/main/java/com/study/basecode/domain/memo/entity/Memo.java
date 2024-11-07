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

    // 낙관적 락일때 사용
    // 버전 명시 필요
//    @Version
//    private Long version;

    private String name;

    private String title;

    private String contents;

    private Long memoLike;

    public Memo(MemoRequsetDto memoRequsetDto) {
        name = memoRequsetDto.getName();
        title = memoRequsetDto.getTitle();
        contents = memoRequsetDto.getContents();
        memoLike = 0L;
    }

    public Memo patchMemo(MemoRequsetDto memoRequsetDto) {
        name = memoRequsetDto.getName();
        title = memoRequsetDto.getTitle();
        contents = memoRequsetDto.getContents();
        return this;
    }

    public void increaseLike() {
        memoLike += 1;
    }
}
