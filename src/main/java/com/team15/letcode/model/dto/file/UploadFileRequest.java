package com.team15.letcode.model.dto.file;

import java.io.Serializable;
import lombok.Data;

@Data
public class UploadFileRequest implements Serializable {

    /**
     * 业务
     */
    private String biz;

    private static final long serialVersionUID = 1L;
}