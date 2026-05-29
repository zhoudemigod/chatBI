import { defineStore } from 'pinia';
import { ref } from 'vue';

import { generateDashboard, type GenerateDashboardCommand } from '@/services/dashboard';
import type { WorkspaceSession } from '@/types/dashboard';

const menuItems = [
  { key: 'chat', label: '对话', icon: 'chatbubble-ellipses-outline', hint: '与模型进行需求沟通' },
  { key: 'template', label: '模版', icon: 'copy-outline', hint: '查看可直接复用的大屏模版' },
  { key: 'datasource', label: '数据源管理', icon: 'server-outline', hint: '管理接口、文件和数据库配置' },
  { key: 'skeleton', label: '骨架', icon: 'grid-outline', hint: '规划页面结构与信息区域' },
  { key: 'asset', label: '素材', icon: 'library-outline', hint: '' }
] as const;

const defaultSession: WorkspaceSession = {
  workspaceTitle: 'ChatBI Console',
  workspaceSubtitle: '对话式 BI 大屏构建工作台',
  activeMenu: 'chat',
  menuItems: [...menuItems],
  suggestions: [
    {
      id: 's1',
      title: '销售驾驶舱',
      description: '生成一个覆盖销售额、区域贡献、门店排名的经营总览大屏'
    },
    {
      id: 's2',
      title: '运营周报屏',
      description: '围绕拉新、转化、留存做一个适合日常复盘的运营看板'
    },
    {
      id: 's3',
      title: '工业监控屏',
      description: '突出设备在线率、异常告警和工厂运行状态的监控场景'
    }
  ],
  messages: []
};

export const useDashboardStore = defineStore('dashboard', () => {
  const loading = ref(false);
  const session = ref<WorkspaceSession>(defaultSession);
  const errorMessage = ref('');

  async function submit(command: GenerateDashboardCommand) {
    loading.value = true;
    errorMessage.value = '';

    try {
      session.value = await generateDashboard(command, session.value.messages);
    } catch (error) {
      errorMessage.value = error instanceof Error ? error.message : '生成失败，请稍后重试';
    } finally {
      loading.value = false;
    }
  }

  function applySuggestion(prompt: string) {
    return submit({ prompt });
  }

  return {
    loading,
    session,
    errorMessage,
    submit,
    applySuggestion
  };
});
