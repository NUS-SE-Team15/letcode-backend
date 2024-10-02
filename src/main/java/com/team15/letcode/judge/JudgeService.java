package com.team15.letcode.judge;

import com.team15.letcode.judge.codesandbox.model.ExecuteCodeResponse;
import com.team15.letcode.model.entity.QuestionSubmit;
import com.team15.letcode.model.vo.QuestionSubmitVO;

public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
