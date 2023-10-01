package com.procorp.chat.dtos;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatHistoryDTO {
    private String msg;
    private String timestamp;
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChatHistoryDTO other = (ChatHistoryDTO) obj;
        if (!Objects.equals(this.msg, other.msg)) {
            return false;
        }
        return Objects.equals(this.timestamp, other.timestamp);
    }
    @Override
    public int hashCode() {
        int hash = 17;
        hash = 37 * hash + (msg == null ? 0 : msg.hashCode());
        hash = 37 * hash + (timestamp == null ? 0 : Objects.requireNonNull(msg).hashCode());
        return hash;
    }
//    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
//    public ChatHistoryDTO(@JsonProperty("msg") String id, @JsonProperty("timestamp") String name) {
//        this.msg = msg;
//        this.timestamp = timestamp;
//    }

}
