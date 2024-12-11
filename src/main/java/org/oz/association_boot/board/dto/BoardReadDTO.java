package org.oz.association_boot.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BoardReadDTO {
    private String title;
    private String writer;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regDate;

    private List<String> attachFileNames;
}
