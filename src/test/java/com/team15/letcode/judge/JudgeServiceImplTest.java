package com.team15.letcode.judge;

import com.team15.letcode.exception.BusinessException;
import com.team15.letcode.model.entity.Question;
import com.team15.letcode.model.entity.QuestionSubmit;
import com.team15.letcode.model.enums.QuestionSubmitStatusEnum;
import com.team15.letcode.service.QuestionService;
import com.team15.letcode.service.QuestionSubmitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest // 加载 Spring 上下文
@AutoConfigureMockMvc // 如果需要 MockMvc，可以添加这个注解
class JudgeServiceImplTest {

    @MockBean
    private QuestionSubmitService questionSubmitService; // Mock 依赖

    @MockBean
    private QuestionService questionService;

    @Autowired
    private JudgeService judgeService; // 真实的服务

    @Test
    void doJudge_questionNotExist() {
        QuestionSubmit questionSubmitTest = new QuestionSubmit();
        questionSubmitTest.setId(1L);
        questionSubmitTest.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());

        // Mock judgeService.doJudge() 返回 BusinessException
        when(questionSubmitService.getById(questionSubmitTest.getId())).thenReturn(questionSubmitTest);

        // 抛出异常的测试
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            judgeService.doJudge(questionSubmitTest.getId());
        });

        assertEquals("题目不存在", thrown.getMessage());
    }

    @Test
    void doJudge_questionSubmitInfoNotExist() {
        QuestionSubmit questionSubmitTest = new QuestionSubmit();
        questionSubmitTest.setId(1L);
        questionSubmitTest.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());

        // 抛出异常的测试
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            judgeService.doJudge(questionSubmitTest.getId());
        });

        assertEquals("提交信息不存在", thrown.getMessage());
    }

    @Test
    void doJudge_questionWaiting() {
        QuestionSubmit questionSubmit = new QuestionSubmit();
        Question question = new Question();
        questionSubmit.setQuestionId(1L);
        questionSubmit.setId(1L);
        questionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());


        // Mock judgeService.doJudge() 返回 BusinessException
        when(questionSubmitService.getById(questionSubmit.getId())).thenReturn(questionSubmit);

        when(questionService.getById(questionSubmit.getId())).thenReturn(question);

        // 抛出异常的测试
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            judgeService.doJudge(1L);
        });

        assertEquals("题目正在判题中", thrown.getMessage());
    }

    @Test
    void doJudge_questionStatusUpdateError1() {
        QuestionSubmit questionSubmit = new QuestionSubmit();
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(1L);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update1 = false;
        Question question = new Question();

        questionSubmit.setQuestionId(1L);
        questionSubmit.setId(1L);
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setLanguage("java");
        questionSubmit.setCode("int main");




        // Mock judgeService.doJudge() 返回 BusinessException
        when(questionSubmitService.getById(questionSubmit.getId())).thenReturn(questionSubmit);

        when(questionService.getById(questionSubmit.getId())).thenReturn(question);

        when(questionSubmitService.updateById(questionSubmitUpdate)).thenReturn(update1);

        // 抛出异常的测试
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            judgeService.doJudge(1L);
        });

        assertEquals("题目状态更新错误1", thrown.getMessage());
    }

    @Test
    void doJudge_questionStatusUpdateError2() {
        QuestionSubmit questionSubmit = new QuestionSubmit();
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        QuestionSubmit questionSubmitUpdate2 = new QuestionSubmit();

        questionSubmitUpdate.setId(1L);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());

        boolean update1 = true;
        boolean update2 = false;

        Question question = new Question();

        questionSubmit.setQuestionId(1L);
        questionSubmit.setId(1L);
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setLanguage("java");
        questionSubmit.setCode("int main");
        question.setJudgeCase("[{\"input\":\"1\",\"output\":\"2\"}]");

        questionSubmitUpdate2.setQuestionId(1L);
        questionSubmitUpdate2.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate2.setJudgeInfo("{\"message\":\"Wrong Answer\",\"memory\":100,\"time\":100}");

        // Mock judgeService.doJudge() 返回 BusinessException
        when(questionSubmitService.getById(questionSubmit.getId())).thenReturn(questionSubmit);

        when(questionService.getById(questionSubmit.getId())).thenReturn(question);

        when(questionSubmitService.updateById(questionSubmitUpdate)).thenReturn(update1);

        when(questionSubmitService.updateById(questionSubmitUpdate2)).thenReturn(update2);
        // 抛出异常的测试
        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            judgeService.doJudge(1L);
        });

        assertEquals("题目状态更新错误2", thrown.getMessage());
    }


}