package org.oz.association_boot.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthProducerDTO {
    private String creatorId;
    private String creatorName;
    private String creatorEmail;
    private String creatorPhone;
    private String creatorPassword;
}
