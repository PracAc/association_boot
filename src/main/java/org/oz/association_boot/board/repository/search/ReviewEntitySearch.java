package org.oz.association_boot.board.repository.search;

import org.oz.association_boot.board.dto.ReviewListDTO;

import java.util.List;

public interface ReviewEntitySearch {

    List<ReviewListDTO> listByBno(Long bno);
}
