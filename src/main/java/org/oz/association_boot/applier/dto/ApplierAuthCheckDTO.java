package org.oz.association_boot.applier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplierAuthCheckDTO {
    private Long ano;
    private String email;
    private String authCode;
}
