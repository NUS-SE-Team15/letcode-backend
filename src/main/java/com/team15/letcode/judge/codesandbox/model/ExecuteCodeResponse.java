package com.team15.letcode.judge.codesandbox.model;

import com.team15.letcode.model.dto.questionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    private List<String> outputList;

    private String message;

    private Integer status;

    private JudgeInfo judgeInfo;

}
