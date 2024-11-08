package com.team15.letcode.model.dto.post;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class PostAddRequest implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 题目 ID
     */
    private Long questionId;  // 新增字段，用于关联题目

    private static final long serialVersionUID = 1L;
}
