package org.oz.association_boot.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.board.dto.ReviewAddDTO;
import org.oz.association_boot.board.dto.ReviewReadDTO;
import org.oz.association_boot.board.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board/review")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("{bno}")
    public ResponseEntity<Long> postReview(@PathVariable("bno") Long bno, @RequestBody ReviewAddDTO reviewAddDTO) {

        return ResponseEntity.ok().body(reviewService.addReview(bno, reviewAddDTO).getAsLong());
    }

    @GetMapping("{rno}")
    public ResponseEntity<ReviewReadDTO> getReview(@PathVariable("rno") Long rno) {

        return ResponseEntity.ok().body(reviewService.getReviewOne(rno).get());
    }

    @PutMapping("edit/{rno}")
    public ResponseEntity<Long> putReview(@PathVariable("rno") Long rno, @RequestBody String content) {
        log.info(content);
        return ResponseEntity.ok().body(reviewService.modifyReview(rno,content).getAsLong());
    }

    @PutMapping("delete/{rno}")
    public ResponseEntity<Long> deleteReview(@PathVariable("rno") Long rno) {
        return ResponseEntity.ok().body(reviewService.deleteReview(rno).getAsLong());
    }
}
