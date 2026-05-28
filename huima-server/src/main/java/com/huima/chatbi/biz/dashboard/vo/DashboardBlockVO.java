package com.huima.chatbi.biz.dashboard.vo;

import java.util.Collections;
import java.util.List;

public class DashboardBlockVO {

    private Long id;
    private String title;
    private String type;
    private String summary;
    private List<ChartItemVO> items;

    public static DashboardBlockVO metric(Long id, String title, String summary, Integer value) {
        DashboardBlockVO block = new DashboardBlockVO();
        block.setId(id);
        block.setTitle(title);
        block.setType("metric");
        block.setSummary(summary);
        block.setItems(Collections.singletonList(new ChartItemVO(title, value)));
        return block;
    }

    public static DashboardBlockVO bar(Long id, String title, String summary, List<ChartItemVO> items) {
        DashboardBlockVO block = new DashboardBlockVO();
        block.setId(id);
        block.setTitle(title);
        block.setType("bar");
        block.setSummary(summary);
        block.setItems(items);
        return block;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<ChartItemVO> getItems() {
        return items;
    }

    public void setItems(List<ChartItemVO> items) {
        this.items = items;
    }
}

