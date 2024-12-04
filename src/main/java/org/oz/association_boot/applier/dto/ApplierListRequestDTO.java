package org.oz.association_boot.applier.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.oz.association_boot.applier.domain.ApplierStatus;
import org.oz.association_boot.common.dto.PageRequestDTO;

@ToString
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class ApplierListRequestDTO extends PageRequestDTO {
    private Long ano;
    private String bizNo;
    private String name;
    private java.time.LocalDate regDate;
    private ApplierStatus regStatus;
}
