package com.team15.letcode.judge;

import com.team15.letcode.judge.strategy.DefaultJudgeStrategy;
import com.team15.letcode.judge.strategy.JudgeContext;
import com.team15.letcode.judge.strategy.JudgeStrategy;
import com.team15.letcode.model.dto.questionsubmit.JudgeInfo;
import com.team15.letcode.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();

        return judgeStrategy.doJudge(judgeContext);
    }

}
