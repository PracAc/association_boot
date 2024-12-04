package org.oz.association_boot.adminlogin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AdminLoginDTO {
    private String adminID;
    private String adminPw;
    private String adminName;

    public AdminLoginDTO(String adminID, String adminPw, String adminName) {
        this.adminID = adminID;
        this.adminPw = adminPw;
        this.adminName = adminName;
    }
}
