package org.oz.association_boot.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@ToString
@SuperBuilder
@Getter
@Setter
public class BoardReadWithReviewDTO extends BoardReadDTO {
    private List<ReviewListDTO> reviewList;
}
