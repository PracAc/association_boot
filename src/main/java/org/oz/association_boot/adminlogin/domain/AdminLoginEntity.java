package org.oz.association_boot.adminlogin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="admin_login")
public class AdminLoginEntity {

    @Id
    @Column(name = "adminId", nullable = false)
    private String adminId;

    @Column(name="adminPw", nullable = false)
    private String adminPw;

    @Column(name="adminName", nullable = false)
    private String adminName;

    public void changePassword(String pw){
        this.adminPw = pw;
    }
}
