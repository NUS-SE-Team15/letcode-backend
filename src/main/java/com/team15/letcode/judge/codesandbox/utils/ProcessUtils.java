package com.team15.letcode.judge.codesandbox.utils;

import com.team15.letcode.judge.codesandbox.model.ExecuteMessage;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcessUtils {
    public static ExecuteMessage runProcessAndGetMessage(Process runProcess, String opName){
        ExecuteMessage executeMessage = new ExecuteMessage();

        try {
            //等待程序执行，获取错误码
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            int exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);
            //正常退出
            if(exitValue == 0){
                System.out.println(opName + "success");
                //分批获取进程的正常输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                StringBuilder compileOutputStringBuilder = new StringBuilder();
                //逐行读取
                String compileOutputLine;
                while((compileOutputLine = bufferedReader.readLine())!= null){
                    compileOutputStringBuilder.append(compileOutputLine);
                }
                executeMessage.setMessage(compileOutputStringBuilder.toString());
            } else {
                //异常退出
                System.out.println(opName + "failed, error code: " + exitValue);
                //分批获取进程的正常输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                StringBuilder compileOutputStringBuider = new StringBuilder();
                //逐行读取
                String compileOutputLine;
                while((compileOutputLine = bufferedReader.readLine())!= null){
                    compileOutputStringBuider.append(compileOutputLine);
                }
                executeMessage.setMessage(compileOutputStringBuider.toString());
                //分批获取进程的错误输出
                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                StringBuilder errorOutputStringBuider = new StringBuilder();
                //逐行获取
                String errorOutputLine;
                while((errorOutputLine = errorBufferedReader.readLine())!= null){
                    errorOutputStringBuider.append(errorOutputLine);
                }
                executeMessage.setErrorMessage(compileOutputStringBuider.toString());
            }
            stopWatch.stop();
            executeMessage.setTime(stopWatch.getLastTaskTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }

}
