package org.oz.association_boot.applier.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.applier.domain.ApplierEntity;
import org.oz.association_boot.applier.domain.QApplierEntity;
import org.oz.association_boot.applier.dto.ApplierListDTO;
import org.oz.association_boot.common.dto.PageRequestDTO;
import org.oz.association_boot.common.dto.PageResponseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class ApplierSearchImpl extends QuerydslRepositorySupport implements ApplierSearch {
    public ApplierSearchImpl() {
        super(ApplierEntity.class);
    }

    @Override
    public PageResponseDTO<ApplierListDTO> getListApplier(PageRequestDTO pageRequestDTO) {

        log.info("===============Board List===========");

        QApplierEntity applierEntity = QApplierEntity.applierEntity;

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("ano").descending());

        JPQLQuery<ApplierEntity> query = from(applierEntity);
        query.where(applierEntity.delFlag.eq(false));

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<ApplierListDTO> dtoQuery = query.select(Projections.bean(
                ApplierListDTO.class,
                applierEntity.ano,
                applierEntity.name,
                applierEntity.regStatus,
                applierEntity.regDate
        ));

        List<ApplierListDTO> dtoList = dtoQuery.fetch();

        long totalCount = dtoQuery.fetchCount();

        return PageResponseDTO.<ApplierListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
