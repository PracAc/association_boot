package org.oz.association_boot.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthConsumeDTO {
    private Long ano;
    private String email;
    private String authCode;
}
