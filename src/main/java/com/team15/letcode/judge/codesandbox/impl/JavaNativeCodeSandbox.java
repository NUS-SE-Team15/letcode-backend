package com.team15.letcode.judge.codesandbox.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.team15.letcode.judge.codesandbox.CodeSandbox;
import com.team15.letcode.judge.codesandbox.model.ExecuteCodeRequest;
import com.team15.letcode.judge.codesandbox.model.ExecuteCodeResponse;
import com.team15.letcode.judge.codesandbox.model.ExecuteMessage;
import com.team15.letcode.judge.codesandbox.utils.ProcessUtils;
import com.team15.letcode.model.dto.questionsubmit.JudgeInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JavaNativeCodeSandbox implements CodeSandbox {

    private static final String GLOBAL_CODE_DIR_NAME = "tmpcode";

    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        //1. 把用户代码保存为文件
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        //判断全局代码目录是否存在，没有则新建
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }

        //把用户的代码隔离存放
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;
        //c:tmpcode -> uuid -> main.java
        //                -> main.class
        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);

        //2. 编译代码，得到class文件
        String compileCmd = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess, "Compile");
            System.out.println(executeMessage);
        } catch (Exception e) {
            return getErrorResponse(e);
        }

        //3. 执行代码，得到输出结果
        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        for (String inputArgs : inputList) {
            String runCmd = String.format("java -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath, inputArgs);
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "Run");
                executeMessageList.add(executeMessage);
                System.out.println(executeMessage);
            } catch (Exception e) {
                return getErrorResponse(e);
            }
        }

        //4. 收集整理输出结果
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> outputList = new ArrayList<>();
        //取用时最大值，便于判断是否超时
        long maxTime = 0;
        for (ExecuteMessage executeMessage : executeMessageList) {
            String errorMessage = executeMessage.getErrorMessage();
            if(!StrUtil.isBlank(errorMessage)) {
                executeCodeResponse.setMessage(errorMessage);
                //执行中存在错误
                executeCodeResponse.setStatus(3);
                break;
            }
            outputList.add(executeMessage.getMessage());
            Long time = executeMessage.getTime();
            if (time != null) {
                maxTime = Math.max(maxTime, time);
            }
        }
        //正常运行完成
        if(outputList.size() == executeMessageList.size()) {
            executeCodeResponse.setStatus(1);
        }
        executeCodeResponse.setOutputList(outputList);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(maxTime);
        judgeInfo.setMemory(1L);
        executeCodeResponse.setJudgeInfo(judgeInfo);

        //5. 文件清理
        if (userCodeFile.getParentFile() != null) {
            boolean del = FileUtil.del(userCodeParentPath);
            System.out.println("Delete" + (del ? "success" : "failed"));
        }
        return executeCodeResponse;
    }

    //6. 错误处理
    private ExecuteCodeResponse getErrorResponse(Throwable e) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(new ArrayList<>());
        executeCodeResponse.setMessage(e.getMessage());
        // 表示代码沙箱错误
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setJudgeInfo(new JudgeInfo());
        return executeCodeResponse;
    }
}
