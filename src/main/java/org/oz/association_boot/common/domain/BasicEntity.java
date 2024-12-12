package org.oz.association_boot.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ToString
public abstract class BasicEntity {

    @Column(name = "del_flag", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean delFlag = Boolean.FALSE;

    @Column(name = "reg_date",updatable = false)
    @CreatedDate
    private LocalDateTime regDate;

    @Column(name = "mod_date")
    @LastModifiedDate
    private LocalDateTime modDate;

    @Column(name = "creator")
    private String creator;

    // Soft Delete Boolean 값 변경
    public void changeDelFlag(Boolean newDelFlag){
        this.delFlag = newDelFlag;
    }

    public void setCreator(String newCreator){
        this.creator = newCreator;
    }
}
