package org.oz.association_boot.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.oz.association_boot.common.dto.PageRequestDTO;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BoardPageRequestDTO extends PageRequestDTO {

    private Integer btype;
}
