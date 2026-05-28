package com.huima.chatbi.biz.dashboard.service.impl;

import com.huima.chatbi.biz.dashboard.dto.DashboardGenerateRequest;
import com.huima.chatbi.biz.dashboard.service.DashboardService;
import com.huima.chatbi.biz.dashboard.vo.ChartItemVO;
import com.huima.chatbi.biz.dashboard.vo.DashboardBlockVO;
import com.huima.chatbi.biz.dashboard.vo.DashboardGenerateResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Override
    public DashboardGenerateResponse generateDashboard(DashboardGenerateRequest request) {
        DashboardGenerateResponse response = new DashboardGenerateResponse();
        response.setThemeName(request.getStylePreference());
        response.setSceneName(request.getSceneType() + "大屏方案");
        response.setLayoutMode("三栏复古编排");
        response.setDescription("基于用户输入需求生成的首版可视化蓝图，强调信息分层、趋势对比和重点指标露出。后续可接入真实大模型输出更丰富的布局与图表建议。");
        response.setBlocks(Arrays.asList(
                DashboardBlockVO.metric(1L, "销售总额", "用于突出整体业务规模", 86),
                DashboardBlockVO.metric(2L, "订单完成率", "用于衡量执行效率", 73),
                DashboardBlockVO.metric(3L, "区域覆盖率", "用于观察业务铺开情况", 61),
                DashboardBlockVO.bar(4L, "区域贡献排行", "展示主要区域贡献差异", Arrays.asList(
                        new ChartItemVO("华东", 88),
                        new ChartItemVO("华南", 76),
                        new ChartItemVO("华北", 63),
                        new ChartItemVO("西南", 51)
                )),
                DashboardBlockVO.bar(5L, "门店经营表现", "适合体现重点门店经营状态", Arrays.asList(
                        new ChartItemVO("南京一店", 82),
                        new ChartItemVO("杭州旗舰店", 74),
                        new ChartItemVO("成都概念店", 65),
                        new ChartItemVO("武汉体验店", 58)
                ))
        ));
        return response;
    }
}

