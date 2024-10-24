package com.study.basecode.domain.memo.dto;

import com.study.basecode.domain.memo.entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemoResponseDto {
    private Long id;
    private String name;
    private String title;
    private String contents;

    public MemoResponseDto(Memo memo) {
        id = memo.getId();
        name = memo.getName();
        title = memo.getTitle();
        contents = memo.getContents();
    }
}
