package org.oz.association_boot.applier.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "applier_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApplierHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ahno;

    private String name;
    private String email;

    private String status;

    @Column(name = "mod_date")
    @LastModifiedDate
    private LocalDateTime modDate;

    private String modifier;

}
