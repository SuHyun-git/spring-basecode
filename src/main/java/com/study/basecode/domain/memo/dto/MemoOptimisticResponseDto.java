package com.study.basecode.domain.memo.dto;

import com.study.basecode.domain.memo.entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemoOptimisticResponseDto {
    private Long id;
    private String name;
    private String title;
    private String contents;
    private Long like;
    private int failureCount;

    public MemoOptimisticResponseDto(Memo memo, int failureCount) {
        id = memo.getId();
        name = memo.getName();
        title = memo.getTitle();
        contents = memo.getContents();
        like = memo.getMemoLike();
        this.failureCount = failureCount;
    }
}
