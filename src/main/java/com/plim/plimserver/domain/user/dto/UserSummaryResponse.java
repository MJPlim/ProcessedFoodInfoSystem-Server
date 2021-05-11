package com.plim.plimserver.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserSummaryResponse {
    private final String userName;
    private final Long favoriteCount;
    private final Long reviewCount;

    public static UserSummaryResponse of(String userName, Long fCount, Long rCount) {
        return new UserSummaryResponse(userName, fCount, rCount);
    }
}
