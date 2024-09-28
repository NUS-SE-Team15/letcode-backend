package com.team15.letcode.model.dto.questionsubmit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeInfo {
    private String message;

    private Long memory;

    private Long time;
}
