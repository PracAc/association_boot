package org.oz.association_boot.applier.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.oz.association_boot.applier.domain.ApplierStatus;

import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
public class ApplierListDTO {
    private Long ano;
    private String name;
    private String bizNo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regDate;

    private ApplierStatus regStatus;
}
