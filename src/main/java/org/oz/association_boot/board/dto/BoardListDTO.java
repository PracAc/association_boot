package org.oz.association_boot.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
public class BoardListDTO {
    private Long bno;
    private String title;
    private String writer;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regDate;

    public BoardListDTO(Long bno, String title, String writer, LocalDateTime regDate) {
        this.bno = bno;
        this.title = title;
        this.writer = writer;
        this.regDate = regDate;
    }
}
