package com.procorp.community.dtos;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommunityChatHistoryDto {
    private long memberId;
    private String msg;
    private String timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CommunityChatHistoryDto that)) return false;

        return new EqualsBuilder().append(getMemberId(), that.getMemberId()).append(getMsg(), that.getMsg()).append(getTimestamp(), that.getTimestamp()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getMemberId()).append(getMsg()).append(getTimestamp()).toHashCode();
    }
}
