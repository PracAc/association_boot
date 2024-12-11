package org.oz.association_boot.board.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.board.domain.BoardEntity;
import org.oz.association_boot.board.domain.QBoardEntity;
import org.oz.association_boot.board.dto.BoardListDTO;
import org.oz.association_boot.board.dto.BoardPageRequestDTO;
import org.oz.association_boot.common.dto.PageResponseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class BoardEntitySearchImpl extends QuerydslRepositorySupport implements BoardEntitySearch{
    public BoardEntitySearchImpl() {
        super(BoardEntity.class);
    }

    @Override
    public PageResponseDTO<BoardListDTO> getListBoard(BoardPageRequestDTO pageRequestDTO) {

        log.info("===============Board List===========");

        QBoardEntity boardEntity = QBoardEntity.boardEntity;

        int type = pageRequestDTO.getBtype() == null ? 1 : pageRequestDTO.getBtype();

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("bno").descending());

        JPQLQuery<BoardEntity> query = from(boardEntity);
        query.where(boardEntity.delFlag.eq(false));
        query.where(boardEntity.btype.eq(type));

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<BoardListDTO> dtoQuery = query.select(Projections.bean(
                BoardListDTO.class,
                boardEntity.bno,
                boardEntity.title,
                boardEntity.writer,
                boardEntity.regDate
        ));

        List<BoardListDTO> dtoList = dtoQuery.fetch();

        long totalCount = dtoQuery.fetchCount();

        return PageResponseDTO.<BoardListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
