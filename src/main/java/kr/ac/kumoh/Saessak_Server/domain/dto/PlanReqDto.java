package kr.ac.kumoh.Saessak_Server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlanReqDto {

    private Long id;
    private Long userId;
    private String planType;
    private Long myplant_id;
    private Boolean done;
    private String date;

}
