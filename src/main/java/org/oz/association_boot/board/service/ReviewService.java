package org.oz.association_boot.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.board.domain.BoardEntity;
import org.oz.association_boot.board.domain.ReviewEntity;
import org.oz.association_boot.board.dto.ReviewAddDTO;
import org.oz.association_boot.board.dto.ReviewReadDTO;
import org.oz.association_boot.board.repository.BoardEntityRepository;
import org.oz.association_boot.board.repository.ReviewEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.OptionalLong;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ReviewService {
    private final BoardEntityRepository boardEntityRepository;
    private final ReviewEntityRepository reviewEntityRepository;

    public OptionalLong addReview(Long bno, ReviewAddDTO reviewAddDTO){

        log.info(reviewAddDTO);
        BoardEntity boardEntity = boardEntityRepository.findById(bno).orElseThrow();
        ReviewEntity reviewEntity = ReviewEntity.builder()
                .board(boardEntity)
                .reviewer(reviewAddDTO.getReviewer())
                .content(reviewAddDTO.getContent())
                .build();

        reviewEntityRepository.save(reviewEntity);

        return OptionalLong.of(reviewEntity.getBoard().getBno());
    }
    public Optional<ReviewReadDTO> getReviewOne(Long rno){
        ReviewEntity reviewEntity = reviewEntityRepository.getReview(rno).get();

        ReviewReadDTO reviewReadDTO = ReviewReadDTO.builder()
                .rno(reviewEntity.getRno())
                .reviewer(reviewEntity.getReviewer())
                .content(reviewEntity.getContent())
                .regDate(reviewEntity.getRegDate())
                .build();

        return Optional.of(reviewReadDTO);
    }
    public OptionalLong modifyReview(Long rno, String content) {

        ReviewEntity reviewEntity = reviewEntityRepository.getReview(rno).orElseThrow();

        reviewEntity.changeContent(content);

        reviewEntityRepository.save(reviewEntity);
        return OptionalLong.of(reviewEntity.getBoard().getBno());
    }
    public OptionalLong deleteReview(Long rno) {
        ReviewEntity reviewEntity = reviewEntityRepository.getReview(rno).orElseThrow();

        reviewEntity.changeDelFlag(true);

        reviewEntityRepository.save(reviewEntity);
        return OptionalLong.of(reviewEntity.getBoard().getBno());
    }
}
