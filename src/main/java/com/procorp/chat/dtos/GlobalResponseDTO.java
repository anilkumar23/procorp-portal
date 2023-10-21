package com.procorp.chat.dtos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GlobalResponseDTO {
    private int statusCode;
    private String status;
    private String msg;
    private Object responseObj;
}
