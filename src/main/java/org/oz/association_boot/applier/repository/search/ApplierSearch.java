package org.oz.association_boot.applier.repository.search;

import org.oz.association_boot.applier.dto.ApplierListDTO;
import org.oz.association_boot.common.dto.PageRequestDTO;
import org.oz.association_boot.common.dto.PageResponseDTO;

public interface ApplierSearch {
    PageResponseDTO<ApplierListDTO> getListApplier(PageRequestDTO pageRequestDTO);
}
