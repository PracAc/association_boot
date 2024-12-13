package org.oz.association_boot.applier.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplierEmailAuthDTO {
    private String email;
    private String authCode;
}
