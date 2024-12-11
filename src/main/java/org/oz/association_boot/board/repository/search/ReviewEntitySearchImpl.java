package org.oz.association_boot.board.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.oz.association_boot.board.domain.QReviewEntity;
import org.oz.association_boot.board.domain.ReviewEntity;
import org.oz.association_boot.board.dto.ReviewListDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ReviewEntitySearchImpl extends QuerydslRepositorySupport implements ReviewEntitySearch {
    public ReviewEntitySearchImpl() {
        super(ReviewEntity.class);
    }

    @Override
    public List<ReviewListDTO> listByBno(Long bno) {
        QReviewEntity reviewEntity = QReviewEntity.reviewEntity;

        JPQLQuery<ReviewEntity> query = from(reviewEntity);
        query.where(reviewEntity.board.bno.eq(bno));
        query.where(reviewEntity.delFlag.eq(false));

        JPQLQuery<ReviewListDTO> dtoQuery = query.select(Projections.bean(
                ReviewListDTO.class,
                reviewEntity.reviewer,
                reviewEntity.content,
                reviewEntity.regDate
        ));

        List<ReviewListDTO> dtoList = dtoQuery.fetch();


        return dtoList;
    }
}
