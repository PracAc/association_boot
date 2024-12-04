package org.oz.association_boot.adminlogin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.adminlogin.domain.AdminLoginEntity;
import org.oz.association_boot.adminlogin.dto.AdminLoginDTO;
import org.oz.association_boot.adminlogin.exception.AdminLoginException;
import org.oz.association_boot.adminlogin.repository.AdminLoginRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AdminLoginService {

    private final AdminLoginRepository adminLoginRepository;

    private final PasswordEncoder passwordEncoder;

    public AdminLoginDTO authenticate(String adminId, String adminPw){

        Optional<AdminLoginEntity> result = adminLoginRepository.findById(adminId);

        AdminLoginEntity admin = result.orElseThrow(() -> AdminLoginException.BAD_AUTH.getException());

        String enterPw = admin.getAdminPw();
        boolean match = passwordEncoder.matches(adminPw, enterPw);

        if(!match){
            log.info("Admin ID and Admin PW do not match");
            throw AdminLoginException.BAD_AUTH.getException();
        }

        AdminLoginDTO adminLoginDTO = new AdminLoginDTO();
        adminLoginDTO.setAdminID(adminId);
        adminLoginDTO.setAdminPw(adminPw);
        adminLoginDTO.setAdminName(admin.getAdminName());

        return adminLoginDTO;
    }
}
