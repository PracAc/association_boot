package org.oz.association_boot.applier.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.oz.association_boot.applier.domain.ApplierStatus;

import java.time.LocalDateTime;
import java.util.List;


@Data
@SuperBuilder
@NoArgsConstructor
public class ApplierReadDTO {
    private Long ano;

    private String name;
    private String email;

    private String zipcode;
    private String roadAddr;
    private String lotNumAddr;
    private String detailAddr;
    private String addrEtc;

    private ApplierStatus regStatus;

    private List<String> attachFileNames;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regDate;

}
