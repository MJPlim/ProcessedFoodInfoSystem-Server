package com.plim.plimserver.domain.food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FoodDTO {
    private final Long lcnsNo; // 인허가번호
    private final String bsshName; // 업소명
    private final Long prdlstReportNo; // 품목제조번호
    private final Long prmsDate; // 허가일자
    private final String prdlstName; // 품목명
    private final String prdlstDCName; // 유형
    private final String rawMaterialName; // 원재료
}
