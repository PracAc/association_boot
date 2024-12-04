package org.oz.association_boot.applier.repository;

import org.oz.association_boot.applier.domain.ApplierEntity;
import org.oz.association_boot.applier.repository.search.ApplierSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplierEntityRepository extends JpaRepository<ApplierEntity,Long>, ApplierSearch {

    @Query("""
    SELECT a FROM ApplierEntity a
    LEFT JOIN FETCH a.attachFiles af
        WHERE a.ano = :ano AND a.delFlag = false
        ORDER BY af.ord DESC
    """)
    Optional<ApplierEntity> getApplierWithFiles(@Param("ano") Long ano);

    @Query("""
    SELECT 
        a
    FROM
        ApplierEntity a
    WHERE
        a.ano = :ano AND a.delFlag = false
    """)
    Optional<ApplierEntity> getApplier(@Param("ano") Long ano);
}
