package org.oz.association_boot.adminlogin.repository;


import org.oz.association_boot.adminlogin.domain.AdminLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminLoginRepository extends JpaRepository<AdminLoginEntity, String> {

}
