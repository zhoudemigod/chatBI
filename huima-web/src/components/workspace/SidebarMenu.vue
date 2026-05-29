<template>
  <aside class="sidebar-menu">
    <div class="sidebar-menu__main">
      <div class="sidebar-menu__brand">
        <span class="sidebar-menu__brand-mark">CB</span>
        <strong>{{ shortTitle }}</strong>
      </div>

      <div class="sidebar-menu__buttons">
        <button
          v-for="item in items"
          :key="item.key"
          type="button"
          class="sidebar-menu__button"
          :class="{ 'sidebar-menu__button--active': item.key === activeKey }"
          :title="item.label"
          @click="$emit('select', item.key)"
        >
          <n-icon size="18">
            <component :is="resolveIcon(item.icon)" />
          </n-icon>
        </button>
      </div>
    </div>

    <div class="sidebar-menu__footer">
      <button
        type="button"
        class="sidebar-menu__button"
        title="设置"
      >
        <n-icon size="18">
          <SettingsOutline />
        </n-icon>
      </button>
    </div>
  </aside>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { NIcon } from 'naive-ui';
import {
  CopyOutline,
  BrowsersOutline,
  ChatbubbleEllipsesOutline,
  GridOutline,
  LibraryOutline,
  SettingsOutline,
  ServerOutline
} from '@vicons/ionicons5';

import type { MenuItem } from '@/types/dashboard';

const props = defineProps<{
  title: string;
  items: MenuItem[];
  activeKey: string;
}>();

defineEmits<{
  select: [key: string];
}>();

const shortTitle = computed(() => props.title.replace(' Console', ''));

const iconMap = {
  'chatbubble-ellipses-outline': ChatbubbleEllipsesOutline,
  'browsers-outline': BrowsersOutline,
  'copy-outline': CopyOutline,
  'server-outline': ServerOutline,
  'grid-outline': GridOutline,
  'library-outline': LibraryOutline,
  'time-outline': ChatbubbleEllipsesOutline
};

function resolveIcon(icon: string) {
  return iconMap[icon as keyof typeof iconMap] ?? ChatbubbleEllipsesOutline;
}
</script>

<style scoped>
.sidebar-menu {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
  padding: 1.15rem 0.45rem;
  border-right: 0;
  background: #ffffff;
}

.sidebar-menu::after {
  content: '';
  position: absolute;
  top: 1rem;
  right: 0;
  bottom: 1rem;
  width: 1px;
  background: rgba(146, 129, 110, 0.12);
}

.sidebar-menu__main {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.4rem;
}

.sidebar-menu__brand {
  display: grid;
  justify-items: center;
  gap: 0.55rem;
}

.sidebar-menu__brand-mark {
  display: inline-grid;
  place-items: center;
  width: 48px;
  height: 48px;
  border-radius: 14px;
  background: #221d18;
  color: #f8f3ea;
  font-weight: 700;
  letter-spacing: 0.08em;
}

.sidebar-menu__brand strong {
  color: #2a231d;
  font-size: 0.82rem;
  letter-spacing: 0.08em;
}

.sidebar-menu__buttons {
  display: grid;
  gap: 0.85rem;
}

.sidebar-menu__footer {
  display: grid;
  justify-items: center;
  padding-top: 1rem;
}

.sidebar-menu__button {
  display: inline-grid;
  place-items: center;
  width: 52px;
  height: 52px;
  border: 1px solid rgba(49, 40, 31, 0.08);
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.52);
  color: #302821;
  cursor: pointer;
  transition: transform 0.18s ease, background-color 0.18s ease, border-color 0.18s ease;
}

.sidebar-menu__button:hover {
  transform: translateY(-1px);
}

.sidebar-menu__button--active {
  background: rgba(39, 33, 27, 0.1);
  border-color: rgba(39, 33, 27, 0.18);
}

@media (max-width: 900px) {
  .sidebar-menu {
    padding: 0.95rem 0.3rem;
  }

  .sidebar-menu__brand strong {
    font-size: 0.72rem;
  }

  .sidebar-menu__button {
    width: 48px;
    height: 48px;
  }
}
</style>
