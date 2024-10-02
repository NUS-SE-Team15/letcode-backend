package com.team15.letcode.controller;

import com.team15.letcode.common.ErrorCode;
import com.team15.letcode.exception.BusinessException;
import com.team15.letcode.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.team15.letcode.model.entity.QuestionSubmit;
import com.team15.letcode.model.entity.User;
import com.team15.letcode.model.vo.QuestionSubmitVO;
import com.team15.letcode.service.QuestionService;
import com.team15.letcode.service.UserService;
import com.team15.letcode.service.impl.QuestionSubmitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class QuestionSubmitTests {

    @InjectMocks
    private QuestionSubmitServiceImpl questionSubmitService;

    @Mock
    private QuestionService questionService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testDoQuestionSubmit_InvalidLanguage_ShouldThrowException() {
        // 创建带有无效语言的请求
        QuestionSubmitAddRequest request = new QuestionSubmitAddRequest();
        request.setLanguage("invalid_lang");

        User mockUser = new User();
        mockUser.setId(1L);

        // 验证抛出异常
        BusinessException exception = assertThrows(BusinessException.class,
                () -> questionSubmitService.doQuestionSubmit(request, mockUser));

        assertEquals(ErrorCode.PARAMS_ERROR.getCode(), exception.getCode());
    }


    @Test
    void testGetQuestionSubmitVO_ShouldReturnValidVO() {
        QuestionSubmit mockSubmit = new QuestionSubmit();
        mockSubmit.setUserId(1L);
        mockSubmit.setCode("Sample Code");

        User mockUser = new User();
        mockUser.setId(1L);

        // 模拟数据
        when(userService.isAdmin(mockUser)).thenReturn(false);

        // 执行方法
        QuestionSubmitVO result = questionSubmitService.getQuestionSubmitVO(mockSubmit, mockUser);

        // 验证 VO 的生成
        assertNotNull(result);
        assertEquals("Sample Code", result.getCode());  // 因为 userId 相同，应该能看到代码
    }

    @Test
    void testGetQuestionSubmitVO_OtherUser_ShouldHideCode() {
        QuestionSubmit mockSubmit = new QuestionSubmit();
        mockSubmit.setUserId(2L);  // 另一个用户
        mockSubmit.setCode("Sample Code");

        User mockUser = new User();
        mockUser.setId(1L);  // 当前登录用户

        // 模拟数据
        when(userService.isAdmin(mockUser)).thenReturn(false);

        // 执行方法
        QuestionSubmitVO result = questionSubmitService.getQuestionSubmitVO(mockSubmit, mockUser);

        // 验证代码脱敏
        assertNotNull(result);
        assertNull(result.getCode());  // 因为不是本人，也不是管理员，应该隐藏代码
    }
}
