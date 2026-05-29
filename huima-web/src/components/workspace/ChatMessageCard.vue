<template>
  <article class="chat-card" :class="[`chat-card--${message.role}`]">
    <div class="chat-card__bubble">
      <header class="chat-card__header">
        <div>
          <span class="chat-card__role">{{ message.role === 'assistant' ? 'Assistant' : 'You' }}</span>
          <h3 v-if="message.title">{{ message.title }}</h3>
        </div>
        <span class="chat-card__time">{{ message.timestamp }}</span>
      </header>

      <p class="chat-card__content">{{ message.content }}</p>

      <div v-if="message.tags?.length" class="chat-card__tags">
        <n-tag
          v-for="tag in message.tags"
          :key="tag"
          size="small"
          round
          :bordered="false"
        >
          {{ tag }}
        </n-tag>
      </div>
    </div>
  </article>
</template>

<script setup lang="ts">
import { NTag } from 'naive-ui';

import type { ChatMessage } from '@/types/dashboard';

defineProps<{
  message: ChatMessage;
}>();
</script>

<style scoped>
.chat-card {
  display: flex;
  padding: 0.7rem 0;
}

.chat-card--assistant {
  justify-content: flex-start;
}

.chat-card--user {
  justify-content: flex-end;
}

.chat-card__bubble {
  width: min(100%, 680px);
  padding: 1rem 1.1rem;
  border: 1px solid rgba(49, 40, 31, 0.08);
  border-radius: 20px;
  background: #ffffff;
}

.chat-card--assistant .chat-card__bubble {
  border-top-left-radius: 8px;
  background: #f8f8f8;
}

.chat-card--user .chat-card__bubble {
  border-top-right-radius: 8px;
  background: #1f1f1f;
  color: #ffffff;
}

.chat-card__header {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
}

.chat-card__role {
  color: rgba(83, 68, 53, 0.6);
  font-size: 0.78rem;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.chat-card--user .chat-card__role {
  color: rgba(255, 255, 255, 0.68);
}

.chat-card__header h3 {
  margin: 0.3rem 0 0;
  font-size: 0.98rem;
  color: #221d18;
}

.chat-card--user .chat-card__header h3 {
  color: #ffffff;
}

.chat-card__time {
  color: rgba(84, 70, 57, 0.44);
  white-space: nowrap;
  font-size: 0.82rem;
}

.chat-card--user .chat-card__time {
  color: rgba(255, 255, 255, 0.52);
}

.chat-card__content {
  margin: 0.9rem 0 0;
  white-space: pre-line;
  line-height: 1.75;
  color: rgba(43, 36, 29, 0.92);
}

.chat-card--user .chat-card__content {
  color: rgba(255, 255, 255, 0.96);
}

.chat-card__tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.6rem;
  margin-top: 0.95rem;
}
</style>
