package com.team15.letcode.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team15.letcode.common.ErrorCode;
import com.team15.letcode.exception.BusinessException;
import com.team15.letcode.model.dto.question.QuestionQueryRequest;
import com.team15.letcode.model.entity.Question;
import com.team15.letcode.service.UserService;
import com.team15.letcode.service.impl.QuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTests {

    @InjectMocks
    private QuestionServiceImpl questionServiceImpl;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidQuestion_ValidInput_ShouldPass() {
        Question validQuestion = new Question();
        validQuestion.setTitle("Valid Title");
        validQuestion.setContent("Valid Content");
        validQuestion.setTags("java");
        validQuestion.setAnswer("Valid Answer");

        assertDoesNotThrow(() -> questionServiceImpl.validQuestion(validQuestion, true));
    }

    @Test
    void testValidQuestion_InvalidTitle_ShouldThrowException() {
        Question invalidQuestion = new Question();
        invalidQuestion.setTitle("");  // Invalid title
        invalidQuestion.setContent("Valid Content");
        invalidQuestion.setTags("java");

        BusinessException exception = assertThrows(BusinessException.class,
                () -> questionServiceImpl.validQuestion(invalidQuestion, true));

        assertEquals(ErrorCode.PARAMS_ERROR.getCode(), exception.getCode());
    }

    @Test
    void testGetQueryWrapper_WithValidRequest() {
        QuestionQueryRequest request = new QuestionQueryRequest();
        request.setTitle("Sample Title");
        request.setContent("Sample Content");

        QueryWrapper<Question> queryWrapper = questionServiceImpl.getQueryWrapper(request);

        assertTrue(queryWrapper.getCustomSqlSegment().contains("title"));
        assertTrue(queryWrapper.getCustomSqlSegment().contains("content"));
    }
}

