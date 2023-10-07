package com.procorp.chat.dtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FilterDTO {
    private long memberId;
    private String userSearchKey;
    private boolean isCollegeName;
    private boolean isSchoolName;
    private boolean isCompanyName;
}
