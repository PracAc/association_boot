package org.oz.association_boot.applier.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.applier.domain.ApplierEntity;
import org.oz.association_boot.applier.domain.QApplierEntity;
import org.oz.association_boot.applier.dto.ApplierListDTO;
import org.oz.association_boot.applier.dto.ApplierListRequestDTO;
import org.oz.association_boot.common.dto.PageRequestDTO;
import org.oz.association_boot.common.dto.PageResponseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class ApplierSearchImpl extends QuerydslRepositorySupport implements ApplierSearch {
    public ApplierSearchImpl() {
        super(ApplierEntity.class);
    }

    @Override
    public PageResponseDTO<ApplierListDTO> getListApplier(ApplierListRequestDTO pageRequestDTO) {

        log.info("===============Applier List===========");

        QApplierEntity applierEntity = QApplierEntity.applierEntity;

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by(
                        Sort.Order.desc("regDate"),   // regDate 내림차순
                        Sort.Order.desc("ano"))
                );

        JPQLQuery<ApplierEntity> query = from(applierEntity);
        query.where(applierEntity.delFlag.eq(false));

        log.info(applierEntity.regStatus);
        log.info(pageRequestDTO.getRegStatus());
        // 등록번호 검색
        if (pageRequestDTO.getAno() != null){
            query.where(applierEntity.ano.like("%" + pageRequestDTO.getAno() + "%"));
        }
        // 사업자 등록 번호 검색
        if (pageRequestDTO.getBizNo() != null){
            query.where(applierEntity.bizNo.like("%" + pageRequestDTO.getBizNo() + "%"));
        }
        // 성명(대표자) 검색
        if (pageRequestDTO.getName() != null){
            query.where(applierEntity.name.like("%" + pageRequestDTO.getName() + "%"));
        }
        // 등록 날짜 검색
        if (pageRequestDTO.getRegDate() != null) {
            // 시작 시간과 종료 시간 계산
            LocalDateTime startOfDay = pageRequestDTO.getRegDate().atStartOfDay();
            LocalDateTime endOfDay = pageRequestDTO.getRegDate().atTime(23, 59, 59, 999999999);

            query.where(applierEntity.regDate.between(startOfDay, endOfDay));
        }
        // 등록 상태 검색
        if (pageRequestDTO.getRegStatus() != null){
            query.where(applierEntity.regStatus.eq(pageRequestDTO.getRegStatus()));
        }

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<ApplierListDTO> dtoQuery = query.select(Projections.bean(
                ApplierListDTO.class,
                applierEntity.ano,
                applierEntity.name,
                applierEntity.bizNo,
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
