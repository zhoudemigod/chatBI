<template>
  <section class="tool-drawer">
    <header v-if="activeKey !== 'datasource' && activeKey !== 'asset'" class="tool-drawer__header">
      <p v-if="activeItem?.hint">{{ activeItem?.hint }}</p>
      <h2>{{ activeItem?.label }}</h2>
    </header>

    <div v-if="activeKey === 'chat'" class="tool-drawer__group">
      <h3>快捷对话</h3>
      <button
        v-for="item in suggestions"
        :key="item.id"
        type="button"
        class="tool-drawer__action"
        @click="$emit('applySuggestion', item.description)"
      >
        <strong>{{ item.title }}</strong>
        <span>{{ item.description }}</span>
      </button>
    </div>

    <div v-else-if="activeKey === 'template'" class="tool-drawer__group">
      <h3>推荐模版</h3>
      <ul class="tool-drawer__list">
        <li>经营总览模版：适合销售、门店、区域业绩等场景</li>
        <li>运营分析模版：适合拉新、留存、转化等分析场景</li>
        <li>监控中心模版：适合设备、告警、实时状态大屏</li>
      </ul>
    </div>

    <div v-else-if="activeKey === 'datasource'" class="tool-drawer__group tool-drawer__group--datasource">
      <n-input
        v-model:value="datasourceKeyword"
        placeholder="按数据源名称搜索"
        clearable
      />

      <div v-if="filteredDatasources.length" class="tool-drawer__saved-list">
        <article
          v-for="item in filteredDatasources"
          :key="item.id"
          class="tool-drawer__saved-item"
        >
          <div>
            <strong>{{ item.name }}</strong>
            <p>{{ item.type }}</p>
          </div>
          <span>{{ item.status }}</span>
        </article>
      </div>

      <p v-else class="tool-drawer__empty-state">
        {{ datasources.length ? '没有搜索到匹配的数据源。' : '还没有配置数据源，先点击下方“新增”。' }}
      </p>

      <n-button type="primary" color="#1f1f1f" text-color="#ffffff" block @click="$emit('createDatasource')">
        新增
      </n-button>
    </div>

    <div v-else-if="activeKey === 'skeleton'" class="tool-drawer__group">
      <h3>页面骨架</h3>
      <ul class="tool-drawer__list">
        <li>顶部标题区：主标题、副标题、更新时间</li>
        <li>中部指标区：核心 KPI 卡片与状态摘要</li>
        <li>底部内容区：趋势图、排行榜、地图或明细模块</li>
      </ul>
    </div>

    <div v-else-if="activeKey === 'asset'" class="tool-drawer__group tool-drawer__group--asset">
      <n-input
        v-model:value="assetKeyword"
        placeholder="搜索图表、背景、图片、装饰"
        clearable
      />

      <div class="tool-drawer__asset-section">
        <div class="tool-drawer__asset-head">
          <h3>ECharts 图表</h3>
          <span>{{ filteredCharts.length }} 个</span>
        </div>

        <div class="tool-drawer__chart-grid">
          <article
            v-for="chart in filteredCharts"
            :key="chart.id"
            class="tool-drawer__chart-card"
          >
            <ChartPreview :kind="chart.kind" />
            <strong>{{ chart.name }}</strong>
            <p>{{ chart.category }}</p>
            <span>{{ chart.description }}</span>
          </article>
        </div>
      </div>

      <div class="tool-drawer__asset-section">
        <div class="tool-drawer__asset-head">
          <h3>静态资源</h3>
          <span>{{ filteredStaticAssets.length }} 个</span>
        </div>

        <div class="tool-drawer__masonry">
          <article
            v-for="item in filteredStaticAssets"
            :key="item.id"
            class="tool-drawer__asset-card"
            :class="[`tool-drawer__asset-card--${item.kind}`]"
          >
            <div class="tool-drawer__asset-preview">
              <span>{{ item.kindLabel }}</span>
            </div>
            <div class="tool-drawer__asset-body">
              <strong>{{ item.name }}</strong>
              <p>{{ item.kindLabel }}</p>
            </div>
          </article>
        </div>
      </div>

      <p v-if="!filteredCharts.length && !filteredStaticAssets.length" class="tool-drawer__empty-state">
        没有搜索到匹配的素材内容。
      </p>
    </div>

    <div v-if="activeKey !== 'datasource' && historyMessages.length" class="tool-drawer__group">
      <h3>最近回复</h3>
      <ul class="tool-drawer__list">
        <li v-for="message in historyMessages" :key="message.id">
          {{ message.title || message.content.slice(0, 22) }}
        </li>
      </ul>
    </div>
  </section>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
import { NButton, NInput } from 'naive-ui';

import ChartPreview, { type ChartPreviewKind } from './ChartPreview.vue';
import type { ChatMessage, DatasourceRecord, MenuItem, SuggestionCard } from '@/types/dashboard';

const props = defineProps<{
  activeKey: string;
  items: MenuItem[];
  suggestions: SuggestionCard[];
  messages: ChatMessage[];
  datasources?: DatasourceRecord[];
}>();

defineEmits<{
  applySuggestion: [prompt: string];
  createDatasource: [];
}>();

type ChartAsset = {
  id: string;
  kind: ChartPreviewKind;
  name: string;
  category: string;
  description: string;
};

type StaticAsset = {
  id: string;
  name: string;
  kind: 'background' | 'image' | 'decoration';
  kindLabel: string;
};

const datasourceKeyword = ref('');
const assetKeyword = ref('');

const chartAssets: ChartAsset[] = [
  { id: 'chart-1', kind: 'line', name: '折线图', category: '趋势分析', description: '适合时间序列、增长趋势、环比变化。' },
  { id: 'chart-2', kind: 'bar', name: '柱状图', category: '对比分析', description: '适合区域、门店、品类之间的横向对比。' },
  { id: 'chart-3', kind: 'pie', name: '饼图', category: '占比分析', description: '适合份额、结构和组成展示。' },
  { id: 'chart-4', kind: 'radar', name: '雷达图', category: '多维评估', description: '适合多指标综合评分与画像分析。' },
  { id: 'chart-5', kind: 'funnel', name: '漏斗图', category: '转化分析', description: '适合线索、访问、下单等转化链路。' },
  { id: 'chart-6', kind: 'gauge', name: '仪表盘', category: '状态监控', description: '适合目标完成率、在线率、告警处理率。' },
  { id: 'chart-7', kind: 'table', name: '表格', category: '明细展示', description: '适合订单明细、排行榜、表格化数据展示。' },
  { id: 'chart-8', kind: 'china-map', name: '中国地图', category: '地域分布', description: '适合省份分布、区域热度和全国业务覆盖展示。' }
];

const staticAssets: StaticAsset[] = [
  { id: 'asset-1', name: '科技蓝渐变背景', kind: 'background', kindLabel: '背景' },
  { id: 'asset-2', name: '深色工业纹理背景', kind: 'background', kindLabel: '背景' },
  { id: 'asset-3', name: '城市夜景图片', kind: 'image', kindLabel: '图片' },
  { id: 'asset-4', name: '品牌活动主视觉', kind: 'image', kindLabel: '图片' },
  { id: 'asset-5', name: '发光边框装饰', kind: 'decoration', kindLabel: '装饰' },
  { id: 'asset-6', name: '角标线条元素', kind: 'decoration', kindLabel: '装饰' },
  { id: 'asset-7', name: '数据波纹底纹', kind: 'decoration', kindLabel: '装饰' },
  { id: 'asset-8', name: '网格矩阵底图', kind: 'background', kindLabel: '背景' }
];

const activeItem = computed(() =>
  props.items.find((item) => item.key === props.activeKey)
);

const historyMessages = computed(() =>
  props.messages.filter((item) => item.role === 'assistant').slice(-3).reverse()
);

const datasources = computed(() => props.datasources ?? []);

const filteredDatasources = computed(() => {
  const keyword = datasourceKeyword.value.trim().toLowerCase();
  if (!keyword) {
    return datasources.value;
  }

  return datasources.value.filter((item) => item.name.toLowerCase().includes(keyword));
});

const filteredCharts = computed(() => {
  const keyword = assetKeyword.value.trim().toLowerCase();
  if (!keyword) {
    return chartAssets;
  }

  return chartAssets.filter((item) =>
    [item.name, item.category, item.description].join(' ').toLowerCase().includes(keyword)
  );
});

const filteredStaticAssets = computed(() => {
  const keyword = assetKeyword.value.trim().toLowerCase();
  if (!keyword) {
    return staticAssets;
  }

  return staticAssets.filter((item) =>
    [item.name, item.kindLabel].join(' ').toLowerCase().includes(keyword)
  );
});
</script>

<style scoped>
.tool-drawer {
  display: grid;
  align-content: start;
  gap: 1.2rem;
  height: 100%;
  overflow: auto;
  padding-right: 0.2rem;
}

.tool-drawer__header p {
  margin: 0;
  color: #746555;
  font-size: 0.82rem;
  letter-spacing: 0.16em;
  text-transform: uppercase;
}

.tool-drawer__header h2 {
  margin: 0.5rem 0 0;
  color: #201b16;
  font-size: 1.5rem;
}

.tool-drawer__group {
  display: grid;
  gap: 0.85rem;
}

.tool-drawer__group h3 {
  margin: 0;
  color: #302821;
  font-size: 1rem;
}

.tool-drawer__group--datasource,
.tool-drawer__group--asset {
  gap: 1rem;
}

.tool-drawer__action {
  display: grid;
  gap: 0.3rem;
  padding: 0.9rem 1rem;
  border: 1px solid rgba(49, 40, 31, 0.08);
  border-radius: 16px;
  background: #ffffff;
  text-align: left;
  cursor: pointer;
}

.tool-drawer__action strong {
  color: #231e18;
}

.tool-drawer__action span {
  color: #6e5e4c;
  line-height: 1.6;
  font-size: 0.88rem;
}

.tool-drawer__list {
  margin: 0;
  padding-left: 1.1rem;
  color: #463a2f;
  line-height: 1.8;
}

.tool-drawer__saved-list {
  display: grid;
  gap: 0.75rem;
}

.tool-drawer__saved-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  padding: 0.9rem 0.95rem;
  border: 1px solid rgba(49, 40, 31, 0.08);
  border-radius: 14px;
  background: #fbfbfb;
}

.tool-drawer__saved-item strong {
  color: #241f1a;
}

.tool-drawer__saved-item p {
  margin: 0.25rem 0 0;
  color: #6d5e4d;
  font-size: 0.84rem;
}

.tool-drawer__saved-item span {
  color: #5f5142;
  font-size: 0.8rem;
  white-space: nowrap;
}

.tool-drawer__asset-section {
  display: grid;
  gap: 0.8rem;
}

.tool-drawer__asset-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
}

.tool-drawer__asset-head span {
  color: #7b6f63;
  font-size: 0.82rem;
}

.tool-drawer__chart-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 0.75rem;
}

.tool-drawer__chart-card {
  display: grid;
  gap: 0.55rem;
  padding: 0.9rem;
  border: 1px solid rgba(49, 40, 31, 0.08);
  border-radius: 14px;
  background: #ffffff;
}

.tool-drawer__chart-card strong {
  color: #241f1a;
}

.tool-drawer__chart-card p {
  margin: 0;
  color: #7b6f63;
  font-size: 0.82rem;
}

.tool-drawer__chart-card span {
  color: #5f5142;
  font-size: 0.84rem;
  line-height: 1.55;
}

.tool-drawer__masonry {
  column-count: 2;
  column-gap: 0.75rem;
}

.tool-drawer__asset-card {
  break-inside: avoid;
  margin-bottom: 0.75rem;
  border: 1px solid rgba(49, 40, 31, 0.08);
  border-radius: 16px;
  background: #ffffff;
  overflow: hidden;
}

.tool-drawer__asset-preview {
  display: flex;
  align-items: flex-end;
  justify-content: flex-start;
  height: 120px;
  padding: 0.85rem;
  background: linear-gradient(135deg, #f0ece7, #d8d1c8);
}

.tool-drawer__asset-preview span {
  padding: 0.22rem 0.5rem;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.7);
  color: #493d32;
  font-size: 0.76rem;
}

.tool-drawer__asset-card--background .tool-drawer__asset-preview {
  background: linear-gradient(135deg, #d6dff2, #b8c6e4);
}

.tool-drawer__asset-card--image .tool-drawer__asset-preview {
  background: linear-gradient(135deg, #ebe6de, #d6c8b8);
}

.tool-drawer__asset-card--decoration .tool-drawer__asset-preview {
  background: linear-gradient(135deg, #edf0f6, #d8deee);
}

.tool-drawer__asset-body {
  display: grid;
  gap: 0.25rem;
  padding: 0.85rem 0.9rem;
}

.tool-drawer__asset-body strong {
  color: #241f1a;
}

.tool-drawer__asset-body p {
  margin: 0;
  color: #6d5e4d;
  font-size: 0.84rem;
}

.tool-drawer__empty-state {
  margin: 0;
  padding: 1rem;
  border: 1px dashed rgba(49, 40, 31, 0.12);
  border-radius: 14px;
  color: #6d5e4d;
  line-height: 1.6;
}

@media (max-width: 900px) {
  .tool-drawer__chart-grid {
    grid-template-columns: 1fr;
  }

  .tool-drawer__masonry {
    column-count: 1;
  }
}
</style>
