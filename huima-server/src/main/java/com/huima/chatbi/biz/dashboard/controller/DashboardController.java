package com.huima.chatbi.biz.dashboard.controller;

import com.huima.chatbi.common.model.Result;
import com.huima.chatbi.biz.dashboard.dto.DashboardGenerateRequest;
import com.huima.chatbi.biz.dashboard.service.DashboardService;
import com.huima.chatbi.biz.dashboard.vo.DashboardGenerateResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PostMapping("/generate")
    public Result<DashboardGenerateResponse> generate(@Valid @RequestBody DashboardGenerateRequest request) {
        return Result.success(dashboardService.generateDashboard(request));
    }
}

