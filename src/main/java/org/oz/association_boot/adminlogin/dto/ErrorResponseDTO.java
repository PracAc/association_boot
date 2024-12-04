package org.oz.association_boot.adminlogin.dto;

import lombok.Data;

@Data
public class ErrorResponseDTO {

    private int status;
    private String message;
}