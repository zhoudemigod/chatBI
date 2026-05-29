import type { ChatMessage, WorkspaceSession } from '@/types/dashboard';

export interface GenerateDashboardCommand {
  prompt: string;
}

const menuItems = [
  { key: 'chat', label: '对话', icon: 'chatbubble-ellipses-outline', hint: '与模型进行需求沟通' },
  { key: 'template', label: '模版', icon: 'copy-outline', hint: '查看可直接复用的大屏模版' },
  { key: 'datasource', label: '数据源管理', icon: 'server-outline', hint: '管理接口、文件和数据库配置' },
  { key: 'skeleton', label: '骨架', icon: 'grid-outline', hint: '规划页面结构与信息区域' },
  { key: 'asset', label: '素材', icon: 'library-outline', hint: '' }
];

function wait(ms: number) {
  return new Promise((resolve) => {
    window.setTimeout(resolve, ms);
  });
}

function buildAssistantReply(prompt: string): ChatMessage {
  const trimmedPrompt = prompt.trim();
  const title = trimmedPrompt.includes('销售')
    ? '销售分析方案'
    : trimmedPrompt.includes('设备')
      ? '设备监控方案'
      : '运营看板方案';

  return {
    id: `assistant-${Date.now()}`,
    role: 'assistant',
    title,
    timestamp: '刚刚',
    tags: ['Mock', 'BI', '建议'],
    content:
      `已根据你的描述生成首版方案：${trimmedPrompt}。\n\n` +
      '建议先确认页面骨架，再补充数据源映射与图表素材，最后继续细化指标口径和交互细节。'
  };
}

export async function generateDashboard(
  command: GenerateDashboardCommand,
  currentMessages: ChatMessage[]
): Promise<WorkspaceSession> {
  await wait(600);

  const userMessage: ChatMessage = {
    id: `user-${Date.now()}`,
    role: 'user',
    content: command.prompt.trim(),
    timestamp: '刚刚'
  };

  const assistantMessage = buildAssistantReply(command.prompt);

  return {
    workspaceTitle: 'ChatBI Console',
    workspaceSubtitle: '对话式 BI 大屏构建工作台',
    activeMenu: 'chat',
    menuItems,
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
    messages: [...currentMessages, userMessage, assistantMessage]
  };
}
