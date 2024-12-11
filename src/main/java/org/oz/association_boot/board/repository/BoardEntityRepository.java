package org.oz.association_boot.board.repository;

import org.oz.association_boot.board.domain.BoardEntity;
import org.oz.association_boot.board.repository.search.BoardEntitySearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardEntityRepository extends JpaRepository<BoardEntity,Long>, BoardEntitySearch {

    @Query("""
        SELECT DISTINCT b FROM BoardEntity b
        LEFT JOIN FETCH b.attachFiles af
        WHERE b.bno = :bno
        ORDER BY af.ord DESC
    """)
    Optional<BoardEntity> getBoard(@Param("bno") Long bno);

}
