package org.oz.association_boot.adminlogin.dto;

import lombok.Data;

@Data
public class TokenResponseDTO {

    private String adminId;
    private String adminName;
    private String accessToken;
    private String refreshToken;
    private int status;
    private String message;
}
