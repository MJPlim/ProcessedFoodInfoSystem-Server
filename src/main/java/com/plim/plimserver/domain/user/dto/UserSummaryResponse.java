package com.plim.plimserver.domain.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSummaryResponse {
    private final Long favorite;
    private final Long review;
    private final Long comment;

    public static UserSummaryResponse of(Long fCount, Long rCount, Long cCount) {
        return new UserSummaryResponse(fCount, rCount, cCount);
    }
}
