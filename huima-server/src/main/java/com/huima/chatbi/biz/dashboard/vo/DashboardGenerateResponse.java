package com.huima.chatbi.biz.dashboard.vo;

import java.util.List;

public class DashboardGenerateResponse {

    private String themeName;
    private String sceneName;
    private String layoutMode;
    private String description;
    private List<DashboardBlockVO> blocks;

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getLayoutMode() {
        return layoutMode;
    }

    public void setLayoutMode(String layoutMode) {
        this.layoutMode = layoutMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DashboardBlockVO> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<DashboardBlockVO> blocks) {
        this.blocks = blocks;
    }
}

