package com.team15.letcode.judge.codesandbox;

import com.team15.letcode.judge.codesandbox.model.ExecuteCodeRequest;
import com.team15.letcode.judge.codesandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
