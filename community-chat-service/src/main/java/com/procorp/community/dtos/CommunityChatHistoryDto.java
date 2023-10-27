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
    private String msg;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CommunityChatHistoryDto other = (CommunityChatHistoryDto) obj;
        return Objects.equals(this.msg, other.msg);
    }
    @Override
    public int hashCode() {
        int hash = 17;
        hash = 37 * hash + (msg == null ? 0 : msg.hashCode());
        return hash;
    }
}
