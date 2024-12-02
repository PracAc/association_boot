package org.oz.association_boot.applier.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.applier.dto.ApplierListDTO;
import org.oz.association_boot.applier.dto.ApplierReadDTO;
import org.oz.association_boot.applier.service.ApplierService;
import org.oz.association_boot.common.dto.PageRequestDTO;
import org.oz.association_boot.common.dto.PageResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/association/applier")
@RequiredArgsConstructor
@Log4j2
public class ApplierController {

    private final ApplierService applierService;

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<ApplierListDTO>> getList(PageRequestDTO pageRequestDTO) {

        return ResponseEntity.ok().body(applierService.getApplierList(pageRequestDTO));
    }

    @GetMapping("read/{ano}")
    public ResponseEntity<ApplierReadDTO> getRead(@PathVariable("ano") Long ano) {

        return ResponseEntity.ok().body(applierService.getApplierOne(ano).get());
    }
}
