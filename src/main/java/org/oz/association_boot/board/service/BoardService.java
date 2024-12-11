package org.oz.association_boot.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.board.domain.BoardEntity;
import org.oz.association_boot.board.domain.ReviewEntity;
import org.oz.association_boot.board.dto.*;
import org.oz.association_boot.board.repository.BoardEntityRepository;
import org.oz.association_boot.board.repository.ReviewEntityRepository;
import org.oz.association_boot.common.domain.AttachFile;
import org.oz.association_boot.common.dto.PageResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoardService {
    private final BoardEntityRepository boardEntityRepository;
    private final ReviewEntityRepository reviewEntityRepository;

    public PageResponseDTO<BoardListDTO> getList(BoardPageRequestDTO pageRequestDTO){
        log.info("------------------------------------getList");
        log.info(pageRequestDTO.toString());
        return boardEntityRepository.getListBoard(pageRequestDTO);
    }

    public Optional<BoardReadWithReviewDTO> getReadWithReview(Long bno){

        Optional<BoardEntity> boardEntity = boardEntityRepository.getBoard(bno);
        Optional<List<ReviewEntity>> reviewEntities = reviewEntityRepository.getReviewListByBno(bno);

        List<ReviewListDTO> reviewList = reviewEntities
                .orElse(Collections.emptyList())  // 리뷰가 없으면 빈 리스트로 처리
                .stream()
                .map(reviewEntity -> ReviewListDTO.builder()
                        .rno(reviewEntity.getRno())
                        .reviewer(reviewEntity.getReviewer())
                        .content(reviewEntity.getContent())
                        .regDate(reviewEntity.getRegDate())
                        .build())
                .collect(Collectors.toList());

        List<String> fileNames = boardEntity.get().getAttachFiles()
                .stream()
                .map(AttachFile::getFileName)
                .collect(Collectors.toList());

        return Optional.of(BoardReadWithReviewDTO.builder()
                .title(boardEntity.get().getTitle())
                .content(boardEntity.get().getContent())
                .writer(boardEntity.get().getWriter())
                .regDate(boardEntity.get().getRegDate())
                .attachFileNames(fileNames)
                .reviewList(reviewList)
                .build());
    }

    public Optional<BoardReadDTO> getReadOne(Long bno){
        Optional<BoardEntity> result = boardEntityRepository.getBoard(bno);

        List<String> fileNames = result.get().getAttachFiles()
                .stream()
                .map(AttachFile::getFileName)
                .collect(Collectors.toList());


        return Optional.of(BoardReadDTO.builder()
                .title(result.get().getTitle())
                .content(result.get().getContent())
                .writer(result.get().getWriter())
                .regDate(result.get().getRegDate())
                .attachFileNames(fileNames)
                .build());
    }

    public Optional<Long> addBoard(BoardAddDTO boardAddDTO){

        log.info("===============================");
        log.info(boardAddDTO.toString());
        log.info("===============================");


        BoardEntity boardEntity = BoardEntity.builder()
                .title(boardAddDTO.getTitle())
                .content(boardAddDTO.getContent())
                .writer(boardAddDTO.getWriter())
                .btype(boardAddDTO.getBtype())
                .build();

        log.info(boardAddDTO.getUploadFileNames());
        List<String> fileNames = boardAddDTO.getUploadFileNames();
        fileNames.forEach (fileName -> {
            boardEntity.addFile(fileName);
        });

        boardEntityRepository.save(boardEntity);

        return Optional.of(boardEntity.getBno());
    }

    public OptionalLong modifyBoard(BoardModifyDTO boardModifyDTO){
        log.info("=================MODIFIY==============");
        log.info(boardModifyDTO);

        BoardEntity boardEntity = boardEntityRepository.getOne(boardModifyDTO.getBno());

        boardEntity.changeTitle(boardModifyDTO.getTitle());
        boardEntity.changeContent(boardModifyDTO.getContent());

        boardEntity.removeFiles();

        boardModifyDTO.getAttachFileNames().forEach(fileName -> {
            boardEntity.addFile(fileName);
        });

        boardEntityRepository.save(boardEntity);

        return OptionalLong.of(boardEntity.getBno());
    }

    public OptionalLong deleteBoard(Long bno){
        BoardEntity boardEntity = boardEntityRepository.getOne(bno);

        boardEntity.changeDelFlag(true);
        boardEntityRepository.save(boardEntity);

        return OptionalLong.of(boardEntity.getBno());
    }
}
