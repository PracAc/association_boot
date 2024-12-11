package org.oz.association_boot.board.repository.search;


import org.oz.association_boot.board.dto.BoardListDTO;
import org.oz.association_boot.board.dto.BoardPageRequestDTO;
import org.oz.association_boot.common.dto.PageResponseDTO;

public interface BoardEntitySearch{
    PageResponseDTO<BoardListDTO> getListBoard(BoardPageRequestDTO pageRequestDTO);
}
