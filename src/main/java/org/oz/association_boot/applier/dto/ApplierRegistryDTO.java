package org.oz.association_boot.applier.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplierRegistryDTO {

    private String bizNo;
    private String name;
    private String openDate;
    private String email;
    private String phone;

    private List<MultipartFile> files;
    private List<String> uploadFileNames;
}
