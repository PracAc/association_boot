package org.oz.association_boot.board.repository;

import org.oz.association_boot.board.domain.ReviewEntity;
import org.oz.association_boot.board.repository.search.ReviewEntitySearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewEntityRepository extends JpaRepository<ReviewEntity,Long>, ReviewEntitySearch {

    @Query("SELECT r FROM ReviewEntity r WHERE r.rno = :rno AND r.delFlag = false ")
    Optional<ReviewEntity> getReview(@Param("rno") Long rno);

    @Query("SELECT r FROM ReviewEntity r WHERE r.board.bno = :bno AND r.delFlag = false ")
    Optional<List<ReviewEntity>> getReviewListByBno(@Param("bno") Long bno);
}
