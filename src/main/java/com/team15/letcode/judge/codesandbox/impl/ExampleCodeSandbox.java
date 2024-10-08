package com.team15.letcode.judge.codesandbox.impl;

import com.team15.letcode.judge.codesandbox.CodeSandbox;
import com.team15.letcode.judge.codesandbox.model.ExecuteCodeRequest;
import com.team15.letcode.judge.codesandbox.model.ExecuteCodeResponse;
import com.team15.letcode.model.dto.questionsubmit.JudgeInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("Execution successful");
        executeCodeResponse.setStatus(1);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage("Execution successful");
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        executeCodeResponse.setOutputList(inputList);
        return executeCodeResponse;
    }
}
