<template>
  <div class="chat-composer" @keydown.esc="menuOpen = false">
    <n-input
      v-model:value="localValue"
      class="chat-composer__input"
      type="textarea"
      :autosize="{ minRows, maxRows: 6 }"
      :placeholder="placeholder"
      @keydown.enter.exact.prevent="handleSubmit"
    />

    <div class="chat-composer__inside-tools">
      <div class="chat-composer__mode-tags">
        <button
          v-for="mode in visibleModes"
          :key="mode.value"
          type="button"
          class="chat-composer__mode-tag"
          :class="{ 'chat-composer__mode-tag--active': mode.value === selectedMode }"
          @click="selectedMode = mode.value"
        >
          {{ mode.label }}
        </button>
      </div>

      <div class="chat-composer__right-tools">
        <div ref="menuRef" class="chat-composer__select-wrap">
          <button
            type="button"
            class="chat-composer__select"
            :class="{ 'chat-composer__select--open': menuOpen }"
            @click="menuOpen = !menuOpen"
          >
            <span class="chat-composer__select-text">{{ selectedOption.label }}</span>
            <n-icon size="12" class="chat-composer__select-caret">
              <ChevronDownOutline />
            </n-icon>
          </button>

          <div v-if="menuOpen" class="chat-composer__menu">
            <button
              v-for="option in modelOptions"
              :key="option.value"
              type="button"
              class="chat-composer__menu-item"
              :class="{ 'chat-composer__menu-item--active': option.value === selectedModel }"
              @click="selectModel(option.value)"
            >
              <span class="chat-composer__menu-text">{{ option.label }}</span>
              <span v-if="option.value === selectedModel" class="chat-composer__menu-check">✓</span>
            </button>
          </div>
        </div>

        <button
          type="button"
          class="chat-composer__send"
          :disabled="loading || !localValue.trim()"
          @click="handleSubmit"
        >
          <n-icon size="16">
            <ArrowUpOutline />
          </n-icon>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onBeforeUnmount, onMounted, ref, watch } from 'vue';
import { NIcon, NInput } from 'naive-ui';
import { ArrowUpOutline, ChevronDownOutline } from '@vicons/ionicons5';

const props = withDefaults(
  defineProps<{
    loading: boolean;
    minRows?: number;
    placeholder?: string;
    modelValue?: string;
    hasMessages?: boolean;
  }>(),
  {
    minRows: 2,
    placeholder: '请输入你的需求',
    modelValue: '',
    hasMessages: false
  }
);

const emit = defineEmits<{
  submit: [value: string];
  'update:modelValue': [value: string];
}>();

const modelOptions = [
  { value: 'deepseek-v4-pro', label: 'deepseek-v4-pro' },
  { value: 'deepseek-r1', label: 'deepseek-r1' },
  { value: 'gpt-4.1', label: 'gpt-4.1' },
  { value: 'gpt-4o', label: 'gpt-4o' }
] as const;

const modeOptions = [
  { value: 'dashboard', label: 'BI大屏模式' },
  { value: 'report', label: '分析报告模式' }
] as const;

const localValue = ref(props.modelValue);
const selectedModel = ref<(typeof modelOptions)[number]['value']>('deepseek-v4-pro');
const selectedMode = ref<(typeof modeOptions)[number]['value']>('dashboard');
const menuOpen = ref(false);
const menuRef = ref<HTMLElement | null>(null);

const selectedOption = computed(
  () => modelOptions.find((option) => option.value === selectedModel.value) ?? modelOptions[0]
);

const visibleModes = computed(() => {
  if (props.hasMessages) {
    return modeOptions.filter((mode) => mode.value === selectedMode.value);
  }
  return modeOptions;
});

watch(
  () => props.modelValue,
  (value) => {
    localValue.value = value;
  }
);

watch(localValue, (value) => {
  emit('update:modelValue', value);
});

function handleSubmit() {
  const value = localValue.value.trim();
  if (!value) {
    return;
  }

  emit('submit', value);
  localValue.value = '';
}

function selectModel(value: (typeof modelOptions)[number]['value']) {
  selectedModel.value = value;
  menuOpen.value = false;
}

function handleDocumentClick(event: MouseEvent) {
  const target = event.target as Node | null;
  if (menuRef.value && target && !menuRef.value.contains(target)) {
    menuOpen.value = false;
  }
}

onMounted(() => {
  document.addEventListener('mousedown', handleDocumentClick);
});

onBeforeUnmount(() => {
  document.removeEventListener('mousedown', handleDocumentClick);
});
</script>

<style scoped>
.chat-composer {
  position: relative;
  padding: 0.75rem 0.9rem 0.7rem;
  border: 1px solid rgba(49, 40, 31, 0.1);
  border-radius: 24px;
  background: #ffffff;
  box-shadow: 0 14px 30px rgba(58, 47, 36, 0.06);
}

.chat-composer__input :deep(.n-input__border),
.chat-composer__input :deep(.n-input__state-border) {
  display: none;
}

.chat-composer__input :deep(.n-input-wrapper) {
  padding-left: 0;
  padding-right: 0;
  padding-bottom: 2.4rem;
  background: transparent;
  box-shadow: none;
}

.chat-composer__input :deep(textarea) {
  font-size: 0.96rem;
  line-height: 1.6;
}

.chat-composer__inside-tools {
  position: absolute;
  left: 0.85rem;
  right: 0.85rem;
  bottom: 0.65rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 0.8rem;
  background: transparent;
}

.chat-composer__mode-tags {
  display: inline-flex;
  align-items: center;
  gap: 0.45rem;
  min-width: 0;
}

.chat-composer__mode-tag {
  display: inline-flex;
  align-items: center;
  min-height: 34px;
  padding: 0.4rem 0.7rem;
  border: 1px solid rgba(49, 40, 31, 0.08);
  border-radius: 999px;
  background: #f7f7f7;
  color: #5f5142;
  font: inherit;
  font-size: 0.8rem;
  line-height: 1;
  white-space: nowrap;
  cursor: pointer;
  transition: background-color 0.18s ease, border-color 0.18s ease, color 0.18s ease;
}

.chat-composer__mode-tag--active {
  border-color: rgba(39, 33, 27, 0.18);
  background: rgba(39, 33, 27, 0.1);
  color: #2f2821;
}

.chat-composer__right-tools {
  display: inline-flex;
  align-items: center;
  gap: 0.7rem;
  flex: none;
}

.chat-composer__select-wrap {
  position: relative;
}

.chat-composer__select {
  display: inline-flex;
  align-items: center;
  justify-content: space-between;
  gap: 0.5rem;
  width: auto;
  min-height: 36px;
  padding: 0.42rem 0.68rem 0.42rem 0.76rem;
  border: 1px solid rgba(49, 40, 31, 0.08);
  border-radius: 999px;
  background: #f7f7f7;
  color: #241f1a;
  font: inherit;
  font-size: 0.84rem;
  line-height: 1.2;
  cursor: pointer;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.8);
}

.chat-composer__select-text {
  white-space: nowrap;
}

.chat-composer__select--open {
  background: #f2f2f2;
}

.chat-composer__select-caret {
  color: #7b6a58;
}

.chat-composer__menu {
  position: absolute;
  right: 0;
  bottom: calc(100% + 0.55rem);
  width: 210px;
  padding: 0.38rem;
  border: 1px solid rgba(49, 40, 31, 0.08);
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.98);
  box-shadow: 0 14px 30px rgba(58, 47, 36, 0.12);
  backdrop-filter: blur(8px);
}

.chat-composer__menu-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 0.75rem;
  width: 100%;
  padding: 0.58rem 0.72rem;
  border: 0;
  border-radius: 12px;
  background: transparent;
  color: #25201a;
  font: inherit;
  font-size: 0.9rem;
  text-align: left;
  white-space: nowrap;
  cursor: pointer;
}

.chat-composer__menu-text {
  flex: 1;
  min-width: 0;
  white-space: nowrap;
}

.chat-composer__menu-item:hover,
.chat-composer__menu-item--active {
  background: rgba(39, 33, 27, 0.1);
}

.chat-composer__menu-check {
  flex: none;
  margin-left: auto;
  color: #2f2821;
  font-weight: 700;
}

.chat-composer__send {
  display: inline-grid;
  place-items: center;
  width: 32px;
  height: 32px;
  border: 0;
  border-radius: 999px;
  background: #1f1f1f;
  color: #ffffff;
  cursor: pointer;
  transition: transform 0.18s ease, opacity 0.18s ease;
}

.chat-composer__send:hover:not(:disabled) {
  transform: translateY(-1px);
}

.chat-composer__send:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

@media (max-width: 720px) {
  .chat-composer {
    padding: 0.72rem 0.82rem 0.68rem;
  }

  .chat-composer__input :deep(.n-input-wrapper) {
    padding-bottom: 2.6rem;
  }

  .chat-composer__inside-tools {
    left: 0.78rem;
    right: 0.78rem;
    bottom: 0.62rem;
    gap: 0.6rem;
  }

  .chat-composer__mode-tags {
    gap: 0.35rem;
  }

  .chat-composer__mode-tag {
    min-height: 32px;
    padding: 0.36rem 0.56rem;
    font-size: 0.74rem;
  }

  .chat-composer__select {
    min-height: 32px;
  }

  .chat-composer__menu {
    width: 190px;
  }
}
</style>
