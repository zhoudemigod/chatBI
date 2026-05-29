export type ChatRole = 'assistant' | 'user';

export interface MenuItem {
  key: string;
  label: string;
  icon: string;
  hint: string;
}

export interface SuggestionCard {
  id: string;
  title: string;
  description: string;
}

export interface ChatMessage {
  id: string;
  role: ChatRole;
  title?: string;
  content: string;
  timestamp: string;
  tags?: string[];
}

export interface WorkspaceSession {
  workspaceTitle: string;
  workspaceSubtitle: string;
  activeMenu: string;
  menuItems: MenuItem[];
  suggestions: SuggestionCard[];
  messages: ChatMessage[];
}

export interface DatasourceRecord {
  id: string;
  name: string;
  type: 'API' | 'Excel' | 'MySQL';
  status: string;
}

export interface ApiResponse<T> {
  code: string;
  message: string;
  data: T;
  success: boolean;
  timestamp: string;
}
