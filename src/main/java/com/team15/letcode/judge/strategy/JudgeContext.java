package com.team15.letcode.judge.strategy;

import com.team15.letcode.model.dto.question.JudgeCase;
import com.team15.letcode.model.dto.questionsubmit.JudgeInfo;
import com.team15.letcode.model.entity.Question;
import com.team15.letcode.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}
