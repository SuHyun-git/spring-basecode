package com.study.basecode.domain.memo.repository;

import com.study.basecode.domain.memo.entity.Memo;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM Memo m WHERE m.id = :memoId")
    Optional<Memo> findByIdPessimistic(@Param("memoId") Long memoId);
}
