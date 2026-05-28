package com.huima.chatbi.biz.dashboard.vo;

public class ChartItemVO {

    private String label;
    private Integer value;

    public ChartItemVO() {
    }

    public ChartItemVO(String label, Integer value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

