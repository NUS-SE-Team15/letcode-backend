package com.team15.letcode.controller;

import com.team15.letcode.common.BaseResponse;
import com.team15.letcode.common.ResultUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    /**
     * 健康检查接口，用于 AWS ALB 的健康检查
     *
     * @return HTTP 200 响应，表示服务健康
     */
    @GetMapping
    public BaseResponse<String> healthCheck() {
        // 返回 HTTP 200 表示健康状态
        return ResultUtils.success("Service is healthy");
    }
}
