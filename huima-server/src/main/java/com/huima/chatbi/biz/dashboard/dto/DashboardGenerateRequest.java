package com.huima.chatbi.biz.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DashboardGenerateRequest {

    @NotBlank(message = "业务需求不能为空")
    @Size(max = 500, message = "业务需求长度不能超过500")
    private String prompt;

    @NotBlank(message = "风格偏好不能为空")
    @Size(max = 50, message = "风格偏好长度不能超过50")
    private String stylePreference;

    @NotBlank(message = "场景类型不能为空")
    @Size(max = 50, message = "场景类型长度不能超过50")
    private String sceneType;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getStylePreference() {
        return stylePreference;
    }

    public void setStylePreference(String stylePreference) {
        this.stylePreference = stylePreference;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }
}

