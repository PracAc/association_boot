package org.oz.association_boot.applier.domain;

import jakarta.persistence.*;
import lombok.*;
import org.oz.association_boot.common.domain.AttachFile;
import org.oz.association_boot.common.domain.BasicEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Table(name = "applier")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = {"applier_files"})
public class ApplierEntity extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano;

    private String bizNo;
    private String name;
    private String openDate;
    private String email;

    @ElementCollection
    @Builder.Default
    @CollectionTable(
            name = "applier_files",
            joinColumns = @JoinColumn(name = "ano"))
    private Set<AttachFile> attachFiles = new HashSet<>();

    @Builder.Default
    @Column(name = "reg_status")
    private ApplierStatus regStatus = ApplierStatus.PENDING;

    private String authCode;

    @Builder.Default
    private Boolean authCheck = Boolean.FALSE;

    public void addFile(String fileName) {
        if (fileName == null){
            attachFiles = new HashSet<>();
        }
        attachFiles.add(new AttachFile(attachFiles.size(), fileName));
    }
    public void removeFiles() {
        attachFiles.clear();
    }

    public void changeAccepted() {
        this.regStatus = ApplierStatus.ACCEPTED;
    }
    public void changeRejected() {
        this.regStatus = ApplierStatus.REJECTED;
    }

    public void changeAuthCode(String authCode) {
        this.authCode = authCode;
    }
    public void changeAuthCheck(Boolean authCheck) {
        this.authCheck = authCheck;
    }
}
