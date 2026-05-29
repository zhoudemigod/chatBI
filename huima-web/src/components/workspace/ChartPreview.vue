<template>
  <div v-if="props.kind === 'table'" class="chart-preview chart-preview--table" aria-hidden="true">
    <div class="table-preview">
      <div class="table-preview__row table-preview__row--head">
        <span></span>
        <span></span>
        <span></span>
      </div>
      <div class="table-preview__row">
        <span></span>
        <span></span>
        <span></span>
      </div>
      <div class="table-preview__row">
        <span></span>
        <span></span>
        <span></span>
      </div>
      <div class="table-preview__row">
        <span></span>
        <span></span>
        <span></span>
      </div>
    </div>
  </div>

  <div v-else-if="props.kind === 'china-map'" class="chart-preview chart-preview--map" aria-hidden="true">
    <svg viewBox="0 0 180 120" class="china-map-preview" role="img">
      <path
        class="china-map-preview__shape"
        d="M17 43 L24 31 L35 22 L49 19 L64 21 L76 18 L88 21 L99 16 L113 19 L126 27 L139 27 L151 36 L159 49 L157 58 L151 66 L151 74 L144 82 L134 88 L127 96 L117 99 L109 105 L98 103 L90 96 L80 94 L69 99 L56 96 L47 91 L39 90 L30 82 L24 73 L20 62 L17 52 L13 47 Z"
      />
      <path
        class="china-map-preview__island"
        d="M127 97 L133 100 L136 107 L131 112 L124 108 L123 101 Z"
      />
      <path
        class="china-map-preview__island china-map-preview__island--small"
        d="M89 103 L93 106 L91 111 L85 110 L83 106 Z"
      />
      <path class="china-map-preview__line" d="M49 39 L128 39" />
      <path class="china-map-preview__line" d="M54 56 L135 56" />
      <path class="china-map-preview__line" d="M44 72 L121 72" />
      <path class="china-map-preview__line" d="M64 25 L58 86" />
      <path class="china-map-preview__line" d="M87 21 L82 94" />
      <path class="china-map-preview__line" d="M109 24 L105 98" />
      <circle class="china-map-preview__point china-map-preview__point--primary" cx="104" cy="52" r="5.5" />
      <circle class="china-map-preview__point" cx="77" cy="67" r="4.5" />
      <circle class="china-map-preview__point china-map-preview__point--light" cx="123" cy="68" r="4.5" />
    </svg>
  </div>

  <div v-else ref="chartRef" class="chart-preview chart-preview--echart"></div>
</template>

<script setup lang="ts">
import { computed, onBeforeUnmount, onMounted, ref, watch } from 'vue';
import * as echarts from 'echarts/core';
import {
  BarChart,
  FunnelChart,
  GaugeChart,
  LineChart,
  PieChart,
  RadarChart
} from 'echarts/charts';
import {
  GridComponent,
  PolarComponent,
  RadarComponent,
  TooltipComponent
} from 'echarts/components';
import { CanvasRenderer } from 'echarts/renderers';

echarts.use([
  LineChart,
  BarChart,
  PieChart,
  RadarChart,
  FunnelChart,
  GaugeChart,
  GridComponent,
  TooltipComponent,
  RadarComponent,
  PolarComponent,
  CanvasRenderer
]);

export type ChartPreviewKind = 'line' | 'bar' | 'pie' | 'radar' | 'funnel' | 'gauge' | 'table' | 'china-map';

const props = defineProps<{
  kind: ChartPreviewKind;
}>();

const chartRef = ref<HTMLDivElement | null>(null);
let chartInstance: echarts.ECharts | null = null;

const palette = {
  cyan: '#20c7ff',
  blue: '#377dff',
  indigo: '#4f46e5',
  violet: '#7c3aed',
  pink: '#ff4d8d',
  amber: '#f59e0b',
  mint: '#34d399',
  slate: '#dbe7ff'
};

const isEchartKind = computed(() =>
  ['line', 'bar', 'pie', 'radar', 'funnel', 'gauge'].includes(props.kind)
);

const option = computed(() => {
  switch (props.kind) {
    case 'line':
      return {
        animation: false,
        grid: { left: 8, right: 8, top: 18, bottom: 8 },
        xAxis: {
          type: 'category',
          data: ['1', '2', '3', '4', '5', '6'],
          boundaryGap: false,
          axisLine: { show: false },
          axisTick: { show: false },
          axisLabel: { show: false },
          splitLine: { show: false }
        },
        yAxis: {
          type: 'value',
          axisLine: { show: false },
          axisTick: { show: false },
          axisLabel: { show: false },
          splitLine: { show: false }
        },
        series: [
          {
            type: 'line',
            smooth: true,
            symbol: 'none',
            data: [18, 26, 20, 34, 28, 40],
            lineStyle: {
              width: 3,
              color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                { offset: 0, color: palette.cyan },
                { offset: 1, color: palette.blue }
              ])
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(32, 199, 255, 0.32)' },
                { offset: 1, color: 'rgba(55, 125, 255, 0.03)' }
              ])
            }
          }
        ]
      };
    case 'bar':
      return {
        animation: false,
        grid: { left: 8, right: 8, top: 16, bottom: 8 },
        xAxis: {
          type: 'category',
          data: ['A', 'B', 'C', 'D', 'E'],
          axisLine: { show: false },
          axisTick: { show: false },
          axisLabel: { show: false }
        },
        yAxis: {
          type: 'value',
          axisLine: { show: false },
          axisTick: { show: false },
          axisLabel: { show: false },
          splitLine: { show: false }
        },
        series: [
          {
            type: 'bar',
            data: [14, 20, 26, 18, 30],
            barWidth: 14,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: palette.cyan },
                { offset: 1, color: palette.indigo }
              ]),
              borderRadius: [8, 8, 0, 0]
            }
          }
        ]
      };
    case 'pie':
      return {
        animation: false,
        series: [
          {
            type: 'pie',
            radius: ['42%', '70%'],
            center: ['50%', '52%'],
            label: { show: false },
            labelLine: { show: false },
            data: [
              { value: 42, itemStyle: { color: palette.cyan } },
              { value: 26, itemStyle: { color: palette.blue } },
              { value: 18, itemStyle: { color: palette.violet } },
              { value: 14, itemStyle: { color: '#d9e2ff' } }
            ]
          }
        ]
      };
    case 'radar':
      return {
        animation: false,
        radar: {
          radius: '62%',
          splitNumber: 4,
          axisName: { color: '#677189', fontSize: 10 },
          splitArea: { areaStyle: { color: ['#ffffff'] } },
          splitLine: { lineStyle: { color: 'rgba(79, 70, 229, 0.12)' } },
          axisLine: { lineStyle: { color: 'rgba(79, 70, 229, 0.12)' } },
          indicator: [
            { name: '营收', max: 100 },
            { name: '留存', max: 100 },
            { name: '效率', max: 100 },
            { name: '质量', max: 100 },
            { name: '转化', max: 100 }
          ]
        },
        series: [
          {
            type: 'radar',
            data: [
              {
                value: [80, 70, 88, 76, 68],
                areaStyle: { color: 'rgba(124, 58, 237, 0.16)' },
                lineStyle: { color: palette.violet, width: 2 },
                itemStyle: { color: palette.violet }
              }
            ]
          }
        ]
      };
    case 'funnel':
      return {
        animation: false,
        series: [
          {
            type: 'funnel',
            left: '16%',
            top: 12,
            bottom: 12,
            width: '68%',
            gap: 4,
            label: { show: false },
            labelLine: { show: false },
            itemStyle: { borderWidth: 0 },
            data: [
              { value: 100, itemStyle: { color: palette.cyan } },
              { value: 80, itemStyle: { color: palette.blue } },
              { value: 56, itemStyle: { color: palette.violet } },
              { value: 32, itemStyle: { color: palette.pink } }
            ]
          }
        ]
      };
    case 'gauge':
      return {
        animation: false,
        series: [
          {
            type: 'gauge',
            startAngle: 210,
            endAngle: -30,
            radius: '82%',
            progress: {
              show: true,
              width: 12,
              itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                  { offset: 0, color: palette.cyan },
                  { offset: 1, color: palette.mint }
                ])
              }
            },
            axisLine: {
              lineStyle: {
                width: 12,
                color: [[1, '#e8eefb']]
              }
            },
            pointer: { show: false },
            axisTick: { show: false },
            splitLine: { show: false },
            axisLabel: { show: false },
            detail: {
              valueAnimation: false,
              offsetCenter: [0, '10%'],
              fontSize: 18,
              color: palette.blue,
              formatter: '{value}%'
            },
            data: [{ value: 74 }]
          }
        ]
      };
    default:
      return {};
  }
});

function renderChart() {
  if (!isEchartKind.value || !chartRef.value) {
    return;
  }

  if (!chartInstance) {
    chartInstance = echarts.init(chartRef.value);
  }

  chartInstance.setOption(option.value, true);
}

function disposeChart() {
  chartInstance?.dispose();
  chartInstance = null;
}

function resizeChart() {
  chartInstance?.resize();
}

watch(
  () => props.kind,
  () => {
    if (!isEchartKind.value) {
      disposeChart();
      return;
    }
    renderChart();
  }
);

onMounted(() => {
  renderChart();
  window.addEventListener('resize', resizeChart);
});

onBeforeUnmount(() => {
  window.removeEventListener('resize', resizeChart);
  disposeChart();
});
</script>

<style scoped>
.chart-preview {
  width: 100%;
  height: 120px;
  pointer-events: none;
}

.chart-preview--echart {
  min-height: 120px;
}

.chart-preview--table,
.chart-preview--map {
  display: grid;
  place-items: center;
}

.table-preview {
  display: grid;
  width: 86%;
  border: 1px solid #d8d2ca;
  border-radius: 14px;
  overflow: hidden;
  background: #ffffff;
  box-shadow: 0 6px 18px rgba(31, 31, 31, 0.05);
}

.table-preview__row {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  min-height: 22px;
  border-top: 1px solid #e7e1d9;
}

.table-preview__row:first-child {
  border-top: 0;
}

.table-preview__row--head {
  background: #f3f0eb;
}

.table-preview__row span {
  border-left: 1px solid #e7e1d9;
}

.table-preview__row span:first-child {
  border-left: 0;
}

.china-map-preview {
  width: 86%;
  height: 100px;
  overflow: visible;
}

.china-map-preview__shape,
.china-map-preview__island {
  fill: #eef4ff;
  stroke: #4f46e5;
}

.china-map-preview__shape {
  stroke-width: 2;
  filter: drop-shadow(0 8px 16px rgba(79, 70, 229, 0.12));
}

.china-map-preview__island {
  stroke-width: 1.5;
}

.china-map-preview__island--small {
  stroke-width: 1.2;
}

.china-map-preview__line {
  fill: none;
  stroke: rgba(79, 70, 229, 0.22);
  stroke-width: 1;
}

.china-map-preview__point {
  fill: #20c7ff;
}

.china-map-preview__point--primary {
  fill: #7c3aed;
}

.china-map-preview__point--light {
  fill: #ff4d8d;
}
</style>
