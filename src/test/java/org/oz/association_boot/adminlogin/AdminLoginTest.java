package org.oz.association_boot.adminlogin;

import org.junit.jupiter.api.Test;
import org.oz.association_boot.adminlogin.domain.AdminLoginEntity;
import org.oz.association_boot.adminlogin.repository.AdminLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class AdminLoginTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminLoginRepository adminLoginRepository;

    @Test
    @Transactional
    @Commit
    public void testDummies(){

        for (int i = 1; i <= 10; i++) {
            AdminLoginEntity admin = AdminLoginEntity.builder()
                    .adminId("admin" + i)
                    .adminPw(passwordEncoder.encode("1111"))
                    .adminName("kim")
                    .build();
            adminLoginRepository.save(admin);
        }//end for

    }
}
