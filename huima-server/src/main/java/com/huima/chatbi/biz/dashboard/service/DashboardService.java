package com.huima.chatbi.biz.dashboard.service;

import com.huima.chatbi.biz.dashboard.dto.DashboardGenerateRequest;
import com.huima.chatbi.biz.dashboard.vo.DashboardGenerateResponse;

public interface DashboardService {

    DashboardGenerateResponse generateDashboard(DashboardGenerateRequest request);
}

