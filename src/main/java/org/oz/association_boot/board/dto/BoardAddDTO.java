package org.oz.association_boot.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardAddDTO {
    private String title;
    private String writer;
    private String content;

    private Integer btype;

    private List<MultipartFile> files;

    private List<String> uploadFileNames;
}
