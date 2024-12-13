package org.oz.association_boot.applier.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.applier.dto.*;
import org.oz.association_boot.applier.service.ApplierService;
import org.oz.association_boot.common.dto.PageResponseDTO;
import org.oz.association_boot.util.file.CustomFileUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/association/applier")
@RequiredArgsConstructor
@Log4j2
public class ApplierController {

    private final ApplierService applierService;
    private final CustomFileUtil fileUtil;

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<ApplierListDTO>> getList(ApplierListRequestDTO pageRequestDTO) {

        return ResponseEntity.ok().body(applierService.getApplierList(pageRequestDTO));
    }

    @GetMapping("read/{ano}")
    public ResponseEntity<ApplierReadDTO> getRead(@PathVariable("ano") Long ano) {

        return ResponseEntity.ok().body(applierService.getApplierOne(ano).get());
    }

    @PostMapping("registry")
    public ResponseEntity<Long> registryApplier(ApplierRegistryDTO registryDTO){
        log.info("======================registryApplier=========================");

        log.info("registryDTO : {}", registryDTO);
        String uploadFolder = "portfolio";

        List<String> fileNames = fileUtil.saveFiles(registryDTO.getFiles(),uploadFolder);
        log.info(fileNames);
        registryDTO.setUploadFileNames(fileNames);


        return ResponseEntity.ok().body(applierService.registryApplier(registryDTO).get());
    }

    @PutMapping("modify")
    public ResponseEntity<Long> modifyApplierStatus(@RequestBody ApplierModifyDTO modifyDTO){

        log.info(modifyDTO);

        return ResponseEntity.ok().body(applierService.modifyStatus(modifyDTO).get());
    }

    @PostMapping("auth")
    public ResponseEntity<String> checkApplierAuth(@RequestBody ApplierAuthCheckDTO checkDTO){
        log.info("=========Auth Check========");
        return ResponseEntity.ok().body(applierService.checkAuth(checkDTO).get());
    }

    @PostMapping("auth/email")
    public ResponseEntity<String> sendEmailAuthCode(@RequestBody ApplierEmailAuthDTO emailAuthDTO) {
        log.info("=======Email Auth Code========");

        return ResponseEntity.ok().body(applierService.sendEmailAuth(emailAuthDTO.getEmail()).get());
    }
    @PostMapping("auth/emailchk")
    public ResponseEntity<Boolean> checkEmailAuthCode(@RequestBody ApplierEmailAuthDTO emailAuthDTO){
        log.info("=======Email Auth Code========");

        return ResponseEntity.ok().body(applierService.checkEmailAuth(emailAuthDTO.getEmail(), emailAuthDTO.getAuthCode()).get());
    }
}
