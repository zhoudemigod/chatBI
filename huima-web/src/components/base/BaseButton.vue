<template>
  <button class="base-button" :disabled="disabled || loading" :type="type">
    <span v-if="loading">生成中...</span>
    <span v-else><slot /></span>
  </button>
</template>

<script setup lang="ts">
withDefaults(
  defineProps<{
    disabled?: boolean;
    loading?: boolean;
    type?: 'button' | 'submit';
  }>(),
  {
    disabled: false,
    loading: false,
    type: 'button'
  }
);
</script>

<style scoped>
.base-button {
  min-width: 148px;
  padding: 0.9rem 1.3rem;
  border: 1px solid #503d2b;
  border-radius: 999px;
  background: linear-gradient(180deg, #6a533c, #503d2b);
  color: #f8f2e8;
  font-size: 0.95rem;
  letter-spacing: 0.08em;
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  box-shadow: 0 10px 18px rgba(80, 61, 43, 0.2);
}

.base-button:hover:not(:disabled) {
  transform: translateY(-1px);
}

.base-button:disabled {
  cursor: not-allowed;
  opacity: 0.72;
  box-shadow: none;
}
</style>

