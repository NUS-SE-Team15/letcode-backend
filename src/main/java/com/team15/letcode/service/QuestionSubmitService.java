package com.team15.letcode.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team15.letcode.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.team15.letcode.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.team15.letcode.model.entity.QuestionSubmit;
import com.team15.letcode.model.entity.User;
import com.team15.letcode.model.vo.QuestionSubmitVO;

public interface QuestionSubmitService extends IService<QuestionSubmit> {

    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
