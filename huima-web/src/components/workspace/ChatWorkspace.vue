<template>
  <section class="chat-workspace">
    <header class="chat-workspace__topbar">
      <div>
        <p class="chat-workspace__eyebrow">Dashboard Studio</p>
        <h1>{{ title }}</h1>
      </div>
      <p class="chat-workspace__subtitle">{{ subtitle }}</p>
    </header>

    <div v-if="mode === 'landing'" class="chat-workspace__empty">
      <div class="chat-workspace__empty-header">
        <h2>选择一个大屏模版开始创作</h2>
        <p>可以先使用模版快速起稿，也可以直接点击按钮进入创作对话。</p>
      </div>

      <div class="chat-workspace__template-grid">
        <button
          v-for="item in suggestions"
          :key="item.id"
          type="button"
          class="chat-workspace__template-card"
          @click="$emit('applySuggestion', item.description)"
        >
          <span class="chat-workspace__template-tag">Template</span>
          <strong>{{ item.title }}</strong>
          <p>{{ item.description }}</p>
          <span class="chat-workspace__template-action">选择这个模版</span>
        </button>
      </div>

      <div class="chat-workspace__empty-actions">
        <n-button
          type="primary"
          color="#1f1f1f"
          text-color="#ffffff"
          size="large"
          @click="$emit('startCreate')"
        >
          开始创作
        </n-button>
      </div>
    </div>

    <div v-else class="chat-workspace__chat-page" :class="{ 'chat-workspace__chat-page--active': hasMessages }">
      <div v-if="hasMessages" class="chat-workspace__conversation">
        <n-scrollbar>
          <div class="chat-workspace__conversation-inner">
            <ChatMessageCard
              v-for="message in messages"
              :key="message.id"
              :message="message"
            />
          </div>
        </n-scrollbar>
      </div>

      <div v-else class="chat-workspace__chat-intro">
        <h2>开始描述你要创建的大屏</h2>
        <p>输入业务目标、指标、图表和视觉风格，我会在上方逐步生成对话结果。</p>
      </div>

      <footer class="chat-workspace__composer-wrap">
        <ChatComposer
          v-model="prompt"
          class="chat-workspace__composer"
          :has-messages="hasMessages"
          :loading="loading"
          :min-rows="2"
          placeholder="例如：帮我做一个销售经营大屏，包含销售额、区域贡献、门店排行和趋势分析"
          @submit="handleSubmit"
        />
      </footer>
    </div>
  </section>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
import { NButton, NScrollbar } from 'naive-ui';

import type { ChatMessage, SuggestionCard } from '@/types/dashboard';
import ChatComposer from './ChatComposer.vue';
import ChatMessageCard from './ChatMessageCard.vue';

const props = defineProps<{
  mode: 'landing' | 'chat';
  title: string;
  subtitle: string;
  suggestions: SuggestionCard[];
  messages: ChatMessage[];
  loading: boolean;
}>();

const emit = defineEmits<{
  submit: [payload: { prompt: string }];
  applySuggestion: [prompt: string];
  startCreate: [];
}>();

const prompt = ref('');
const hasMessages = computed(() => props.messages.length > 0);

function handleSubmit() {
  const value = prompt.value.trim();
  if (!value) {
    return;
  }

  emit('submit', { prompt: value });
  prompt.value = '';
}
</script>

<style scoped>
.chat-workspace {
  display: grid;
  grid-template-rows: auto minmax(0, 1fr);
  height: 100%;
  background: transparent;
}

.chat-workspace__topbar {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 1rem;
  padding: 0.2rem 0 1rem;
  background: transparent;
}

.chat-workspace__eyebrow {
  margin: 0;
  color: rgba(77, 63, 49, 0.62);
  font-size: 0.75rem;
  letter-spacing: 0.18em;
  text-transform: uppercase;
}

.chat-workspace__topbar h1 {
  margin: 0.35rem 0 0;
  color: #231f1a;
  font-size: 1.2rem;
  line-height: 1.2;
}

.chat-workspace__subtitle {
  margin: 0;
  color: rgba(68, 57, 45, 0.72);
  font-size: 0.88rem;
}

.chat-workspace__empty {
  display: grid;
  align-content: start;
  gap: 1.5rem;
  padding: 1.5rem 0 0;
  overflow: auto;
}

.chat-workspace__empty-header h2 {
  margin: 0;
  color: #201b16;
  font-size: 1.5rem;
}

.chat-workspace__empty-header p {
  margin: 0.6rem 0 0;
  color: #6d5e4d;
  font-size: 0.95rem;
  line-height: 1.6;
}

.chat-workspace__template-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 1rem;
}

.chat-workspace__template-card {
  display: grid;
  gap: 0.75rem;
  padding: 1.1rem;
  border: 1px solid rgba(49, 40, 31, 0.08);
  border-radius: 18px;
  background: #ffffff;
  text-align: left;
  cursor: pointer;
  transition: border-color 0.18s ease, box-shadow 0.18s ease, transform 0.18s ease;
}

.chat-workspace__template-card:hover {
  transform: translateY(-1px);
  border-color: rgba(49, 40, 31, 0.16);
  box-shadow: 0 16px 28px rgba(58, 47, 36, 0.08);
}

.chat-workspace__template-tag {
  color: #85715b;
  font-size: 0.76rem;
  letter-spacing: 0.12em;
  text-transform: uppercase;
}

.chat-workspace__template-card strong {
  color: #231e18;
  font-size: 1rem;
}

.chat-workspace__template-card p {
  margin: 0;
  color: #675745;
  font-size: 0.9rem;
  line-height: 1.65;
}

.chat-workspace__template-action {
  color: #1f1f1f;
  font-size: 0.86rem;
  font-weight: 600;
}

.chat-workspace__empty-actions {
  display: flex;
  justify-content: flex-start;
}

.chat-workspace__chat-page {
  display: grid;
  grid-template-rows: auto auto;
  align-content: center;
  min-height: 0;
  padding: 0.8rem 0 1rem;
}

.chat-workspace__chat-page--active {
  grid-template-rows: minmax(0, 1fr) auto;
  align-content: stretch;
}

.chat-workspace__chat-intro {
  width: min(80%, 980px);
  justify-self: center;
  text-align: center;
  margin-bottom: 1.5rem;
}

.chat-workspace__chat-intro h2 {
  margin: 0;
  color: #201b16;
  font-size: 1.6rem;
}

.chat-workspace__chat-intro p {
  margin: 0.7rem 0 0;
  color: #6d5e4d;
  line-height: 1.7;
}

.chat-workspace__conversation {
  min-height: 0;
  overflow: hidden;
}

.chat-workspace__conversation-inner {
  width: min(80%, 980px);
  margin: 0 auto;
  padding: 0 0 1.25rem;
}

.chat-workspace__composer-wrap {
  display: flex;
  justify-content: center;
  padding-top: 0.4rem;
}

.chat-workspace__composer {
  width: min(80%, 980px);
}

@media (max-width: 1100px) {
  .chat-workspace__template-grid {
    grid-template-columns: 1fr;
  }

  .chat-workspace__chat-intro,
  .chat-workspace__conversation-inner,
  .chat-workspace__composer {
    width: min(88%, 980px);
  }
}

@media (max-width: 720px) {
  .chat-workspace__topbar,
  .chat-workspace__empty {
    padding-left: 0;
    padding-right: 0;
  }

  .chat-workspace__topbar {
    align-items: flex-start;
    flex-direction: column;
  }

  .chat-workspace__chat-page {
    padding: 0.75rem 0;
  }

  .chat-workspace__chat-intro,
  .chat-workspace__conversation-inner,
  .chat-workspace__composer {
    width: 100%;
  }
}
</style>
