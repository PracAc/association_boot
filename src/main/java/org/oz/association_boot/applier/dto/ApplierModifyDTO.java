package org.oz.association_boot.applier.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.oz.association_boot.applier.domain.ApplierStatus;

@Data
@NoArgsConstructor
public class ApplierModifyDTO {
    private Long ano;
    private int status;
}
