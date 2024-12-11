package org.oz.association_boot.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.board.dto.*;
import org.oz.association_boot.board.service.BoardService;
import org.oz.association_boot.common.dto.PageResponseDTO;
import org.oz.association_boot.util.file.CustomFileUtil;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Log4j2
public class BoardController {
    private final BoardService boardService;
    private final CustomFileUtil fileUtil;

    @GetMapping("/list")
    public ResponseEntity<PageResponseDTO<BoardListDTO>> boardList(BoardPageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok().body(boardService.getList(pageRequestDTO));
    }

    @GetMapping("/read/{bno}")
    public ResponseEntity<BoardReadWithReviewDTO> readBoard(@PathVariable("bno") Long bno) {

        return ResponseEntity.ok().body(boardService.getReadWithReview(bno).get());
    }

    @PostMapping("")
    public ResponseEntity<Long> postBoard(BoardAddDTO boardAddDTO) {

        log.info(boardAddDTO);

//        List<String> uploadFileNames = fileUtil.saveFiles(boardAddDTO.getFiles());

//        boardAddDTO.setUploadFileNames(uploadFileNames);
        return ResponseEntity.ok().body(boardService.addBoard(boardAddDTO).get());
    }

    @PutMapping("/edit/{bno}")
    public ResponseEntity<Long> putBoard(@PathVariable("bno") Long bno, BoardModifyDTO boardModifyDTO) {

        log.info("---------------------------EDIT ----------------------------------------");

        log.info("bno: " + bno);
        log.info("boardModifyDTO: " + boardModifyDTO);
//        List<String> uploadFileNames = fileUtil.saveFiles(boardModifyDTO.getFiles());

        if (boardModifyDTO.getAttachFileNames() == null) {
            boardModifyDTO.setAttachFileNames(new ArrayList<>());
        }
//        boardModifyDTO.getAttachFileNames().addAll(uploadFileNames);
//
//        fileUtil.deleteFiles(boardModifyDTO.getDeleteFileNames());

        return ResponseEntity.ok().body(boardService.modifyBoard(boardModifyDTO).getAsLong());
    }

    @GetMapping("/img/{fileName}")
    public ResponseEntity<Resource> getImg(@PathVariable String fileName) {
//        return ResponseEntity.ok().body(fileUtil.getImgtFile(fileName));
        return null;
    }

    @PutMapping("/delete/{bno}")
    public ResponseEntity<Long> deleteBoard(@PathVariable("bno") Long bno,@RequestBody List<String> deleteFileNames) {

        log.info("=================DELETE==================");
        log.info(deleteFileNames);
//        fileUtil.deleteFiles(deleteFileNames);

        return ResponseEntity.ok().body(boardService.deleteBoard(bno).getAsLong());
    }

}
