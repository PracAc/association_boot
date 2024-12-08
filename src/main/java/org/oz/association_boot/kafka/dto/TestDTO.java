package org.oz.association_boot.kafka.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestDTO {
    private Long id;
    private String name;
    private String code;
}
