<template>
  <main class="home-view">
    <div class="home-view__shell">
      <SidebarMenu
        :title="dashboardStore.session.workspaceTitle"
        :items="dashboardStore.session.menuItems"
        :active-key="activeMenuKey"
        @select="handleSelectMenu"
      />

      <aside class="home-view__panel">
        <div class="home-view__panel-header">
          <div>
            <p>功能菜单</p>
            <h2>{{ activeMenuTitle }}</h2>
          </div>
        </div>

        <ToolDrawerContent
          :active-key="activeMenuKey"
          :items="dashboardStore.session.menuItems"
          :suggestions="dashboardStore.session.suggestions"
          :messages="dashboardStore.session.messages"
          :datasources="datasources"
          @apply-suggestion="handleApplySuggestion"
          @create-datasource="handleCreateDatasource"
        />
      </aside>

      <section v-if="showChatWorkspace" class="home-view__chat">
        <ChatWorkspace
          :mode="workspaceMode"
          :title="dashboardStore.session.workspaceTitle"
          :subtitle="dashboardStore.session.workspaceSubtitle"
          :suggestions="dashboardStore.session.suggestions"
          :messages="dashboardStore.session.messages"
          :loading="dashboardStore.loading"
          @submit="dashboardStore.submit"
          @apply-suggestion="handleApplySuggestion"
          @start-create="handleStartCreate"
        />
      </section>

      <section v-else class="home-view__chat">
        <DatasourceWorkspace
          @cancel="handleCancelDatasource"
          @save="handleSaveDatasource"
        />
      </section>
    </div>

    <p v-if="dashboardStore.errorMessage" class="home-view__error">
      {{ dashboardStore.errorMessage }}
    </p>
  </main>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue';

import SidebarMenu from '@/components/workspace/SidebarMenu.vue';
import ChatWorkspace from '@/components/workspace/ChatWorkspace.vue';
import DatasourceWorkspace from '@/components/workspace/DatasourceWorkspace.vue';
import ToolDrawerContent from '@/components/workspace/ToolDrawerContent.vue';
import { useDashboardStore } from '@/stores/dashboard';
import type { DatasourceRecord } from '@/types/dashboard';

const dashboardStore = useDashboardStore();
const activeMenuKey = ref(dashboardStore.session.activeMenu);
const workspaceMode = ref<'landing' | 'chat'>('landing');
const datasourceEditorVisible = ref(false);
const datasources = ref<DatasourceRecord[]>([
  { id: 'mock-api-1', name: '订单中心 API', type: 'API', status: 'Mock 已保存' },
  { id: 'mock-db-1', name: '运营 MySQL 库', type: 'MySQL', status: 'Mock 已保存' }
]);

watch(
  () => dashboardStore.session.activeMenu,
  (menu) => {
    activeMenuKey.value = menu;
  }
);

const activeMenuTitle = computed(
  () => dashboardStore.session.menuItems.find((item) => item.key === activeMenuKey.value)?.label ?? '功能面板'
);

const showChatWorkspace = computed(
  () => !(activeMenuKey.value === 'datasource' && datasourceEditorVisible.value)
);

function handleSelectMenu(key: string) {
  activeMenuKey.value = key;
  if (key !== 'datasource') {
    datasourceEditorVisible.value = false;
  }
}

function handleApplySuggestion(prompt: string) {
  activeMenuKey.value = 'chat';
  workspaceMode.value = 'chat';
  datasourceEditorVisible.value = false;
  dashboardStore.applySuggestion(prompt);
}

function handleStartCreate() {
  workspaceMode.value = 'chat';
}

function handleCreateDatasource() {
  activeMenuKey.value = 'datasource';
  datasourceEditorVisible.value = true;
}

function handleCancelDatasource() {
  datasourceEditorVisible.value = false;
}

function handleSaveDatasource(item: DatasourceRecord) {
  datasources.value.unshift(item);
  datasourceEditorVisible.value = false;
}
</script>

<style scoped>
.home-view {
  height: 100vh;
  padding: 0;
  overflow: hidden;
  background: #ffffff;
}

.home-view__shell {
  display: grid;
  grid-template-columns: 72px minmax(280px, 360px) minmax(0, 1fr);
  gap: 0;
  height: 100vh;
  background: #ffffff;
}

.home-view__panel {
  display: grid;
  grid-template-rows: auto minmax(0, 1fr);
  min-height: 0;
  padding: 1.25rem 1rem 1rem;
  border-right: 1px solid rgba(124, 108, 89, 0.1);
  background: #ffffff;
  overflow: hidden;
}

.home-view__panel-header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 1rem;
}

.home-view__panel-header p {
  margin: 0;
  color: #7b6955;
  font-size: 0.78rem;
  letter-spacing: 0.14em;
  text-transform: uppercase;
}

.home-view__panel-header h2 {
  margin: 0.4rem 0 0;
  color: #251f19;
  font-size: 1.2rem;
}

.home-view__chat {
  min-width: 0;
  min-height: 0;
  padding: 1rem 1.25rem 1rem 2rem;
}

.home-view__error {
  position: fixed;
  right: 1.25rem;
  bottom: 1rem;
  margin: 0;
  padding: 0.8rem 1rem;
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.96);
  border: 1px solid rgba(34, 29, 24, 0.08);
  box-shadow: 0 10px 24px rgba(58, 47, 36, 0.08);
  color: #2f2922;
}

@media (max-width: 1100px) {
  .home-view__shell {
    grid-template-columns: 64px minmax(240px, 300px) minmax(0, 1fr);
  }
}

@media (max-width: 900px) {
  .home-view__shell {
    grid-template-columns: 64px minmax(220px, 260px) minmax(0, 1fr);
  }

  .home-view__panel {
    padding-left: 0.85rem;
    padding-right: 0.85rem;
  }

  .home-view__chat {
    padding-left: 1.4rem;
  }
}

@media (max-width: 720px) {
  .home-view__shell {
    grid-template-columns: 56px minmax(180px, 220px) minmax(0, 1fr);
  }

  .home-view__panel {
    padding-top: 1rem;
  }

  .home-view__panel-header h2 {
    font-size: 1.05rem;
  }

  .home-view__chat {
    padding: 0.75rem 0.75rem 0.75rem 1rem;
  }
}
</style>
