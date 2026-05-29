<template>
  <section class="base-card" :class="{ 'base-card--dense': dense }">
    <header v-if="title" class="base-card__header">
      <h3>{{ title }}</h3>
      <slot name="extra" />
    </header>
    <div class="base-card__body">
      <slot />
    </div>
  </section>
</template>

<script setup lang="ts">
defineProps<{
  title?: string;
  dense?: boolean;
}>();
</script>

<style scoped>
.base-card {
  border: 1px solid rgba(59, 46, 33, 0.18);
  border-radius: 18px;
  background: #ffffff;
  box-shadow: 0 12px 32px rgba(74, 54, 35, 0.08);
  overflow: hidden;
}

.base-card--dense {
  border-radius: 14px;
}

.base-card__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem 1.2rem 0.5rem;
}

.base-card__header h3 {
  margin: 0;
  font-size: 1rem;
  letter-spacing: 0.04em;
}

.base-card__body {
  padding: 1.1rem 1.2rem 1.2rem;
}
</style>
