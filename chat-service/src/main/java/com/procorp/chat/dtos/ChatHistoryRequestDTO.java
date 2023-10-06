package com.procorp.chat.dtos;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistoryRequestDTO {
    private String msg;
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChatHistoryRequestDTO other = (ChatHistoryRequestDTO) obj;
        return Objects.equals(this.msg, other.msg);
    }
    @Override
    public int hashCode() {
        int hash = 17;
        hash = 37 * hash + (msg == null ? 0 : msg.hashCode());
        return hash;
    }
}
