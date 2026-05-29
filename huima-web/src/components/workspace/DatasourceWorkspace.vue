<template>
  <section class="datasource-workspace">
    <header class="datasource-workspace__topbar">
      <div>
        <p class="datasource-workspace__eyebrow">Datasource Setup</p>
        <h1>新增数据源</h1>
      </div>
      <n-button quaternary @click="$emit('cancel')">返回列表</n-button>
    </header>

    <div class="datasource-workspace__content">
      <div class="datasource-workspace__types">
        <button
          v-for="type in datasourceTypes"
          :key="type.value"
          type="button"
          class="datasource-workspace__type"
          :class="{ 'datasource-workspace__type--active': type.value === datasourceType }"
          @click="datasourceType = type.value"
        >
          {{ type.label }}
        </button>
      </div>

      <div class="datasource-workspace__panel">
        <template v-if="datasourceType === 'api'">
          <div class="datasource-workspace__field">
            <label>数据源名称</label>
            <n-input v-model:value="apiForm.name" placeholder="例如：订单中心 API" />
          </div>

          <div class="datasource-workspace__field-grid">
            <div class="datasource-workspace__field">
              <label>请求方式</label>
              <n-select v-model:value="apiForm.method" :options="methodOptions" />
            </div>

            <div class="datasource-workspace__field">
              <label>请求地址</label>
              <n-input v-model:value="apiForm.url" placeholder="https://api.example.com/orders" />
            </div>
          </div>

          <div class="datasource-workspace__field">
            <label>Headers</label>
            <n-input
              v-model:value="apiForm.headers"
              type="textarea"
              :autosize="{ minRows: 3, maxRows: 5 }"
              placeholder="Authorization: Bearer xxx&#10;Content-Type: application/json"
            />
          </div>

          <div class="datasource-workspace__field">
            <label>请求参数</label>
            <n-input
              v-model:value="apiForm.params"
              type="textarea"
              :autosize="{ minRows: 3, maxRows: 5 }"
              placeholder='{"page":1,"pageSize":20}'
            />
          </div>

          <div class="datasource-workspace__field">
            <label>响应示例</label>
            <n-input
              v-model:value="apiForm.responseExample"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 7 }"
              placeholder='{"code":0,"data":[{"name":"华东","value":1280}]}'
            />
          </div>
        </template>

        <template v-else-if="datasourceType === 'excel'">
          <div class="datasource-workspace__field">
            <label>数据源名称</label>
            <n-input v-model:value="excelForm.name" placeholder="例如：销售日报 Excel" />
          </div>

          <div class="datasource-workspace__field">
            <label>文件名称</label>
            <n-input v-model:value="excelForm.fileName" placeholder="例如：sales-report.xlsx" />
          </div>

          <div class="datasource-workspace__field-grid">
            <div class="datasource-workspace__field">
              <label>Sheet 名称</label>
              <n-input v-model:value="excelForm.sheetName" placeholder="Sheet1" />
            </div>

            <div class="datasource-workspace__field">
              <label>表头所在行</label>
              <n-input-number v-model:value="excelForm.headerRow" :min="1" />
            </div>
          </div>

          <div class="datasource-workspace__field">
            <label>字段预览</label>
            <n-input
              v-model:value="excelForm.columns"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 6 }"
              placeholder="例如：日期、区域、销售额、订单量"
            />
          </div>
        </template>

        <template v-else>
          <div class="datasource-workspace__field">
            <label>数据源名称</label>
            <n-input v-model:value="dbForm.name" placeholder="例如：生产 MySQL 库" />
          </div>

          <div class="datasource-workspace__field">
            <label>数据库类型</label>
            <n-input value="MySQL" disabled />
          </div>

          <div class="datasource-workspace__field-grid">
            <div class="datasource-workspace__field">
              <label>主机地址</label>
              <n-input v-model:value="dbForm.host" placeholder="127.0.0.1" />
            </div>

            <div class="datasource-workspace__field">
              <label>端口</label>
              <n-input-number v-model:value="dbForm.port" :min="1" />
            </div>
          </div>

          <div class="datasource-workspace__field-grid">
            <div class="datasource-workspace__field">
              <label>数据库名</label>
              <n-input v-model:value="dbForm.database" placeholder="chatbi" />
            </div>

            <div class="datasource-workspace__field">
              <label>用户名</label>
              <n-input v-model:value="dbForm.username" placeholder="root" />
            </div>
          </div>

          <div class="datasource-workspace__field">
            <label>密码</label>
            <n-input v-model:value="dbForm.password" type="password" placeholder="请输入数据库密码" />
          </div>

          <div class="datasource-workspace__field">
            <label>默认查询表</label>
            <n-input v-model:value="dbForm.tables" placeholder="例如：orders, order_items, users" />
          </div>
        </template>
      </div>
    </div>

    <footer class="datasource-workspace__footer">
      <n-button @click="$emit('cancel')">取消</n-button>
      <n-button type="primary" color="#1f1f1f" text-color="#ffffff" @click="handleSave">
        保存数据源
      </n-button>
    </footer>
  </section>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import { NButton, NInput, NInputNumber, NSelect } from 'naive-ui';

import type { DatasourceRecord } from '@/types/dashboard';

const emit = defineEmits<{
  cancel: [];
  save: [item: DatasourceRecord];
}>();

const datasourceTypes = [
  { value: 'api', label: 'API' },
  { value: 'excel', label: 'Excel' },
  { value: 'mysql', label: 'MySQL' }
] as const;

const methodOptions = [
  { label: 'GET', value: 'GET' },
  { label: 'POST', value: 'POST' },
  { label: 'PUT', value: 'PUT' },
  { label: 'DELETE', value: 'DELETE' }
];

const datasourceType = ref<(typeof datasourceTypes)[number]['value']>('api');

const apiForm = reactive({
  name: '',
  method: 'GET',
  url: '',
  headers: '',
  params: '',
  responseExample: ''
});

const excelForm = reactive({
  name: '',
  fileName: '',
  sheetName: '',
  headerRow: 1 as number | null,
  columns: ''
});

const dbForm = reactive({
  name: '',
  host: '',
  port: 3306 as number | null,
  database: '',
  username: '',
  password: '',
  tables: ''
});

function handleSave() {
  const item =
    datasourceType.value === 'api'
      ? { name: apiForm.name.trim() || 'API 数据源', type: 'API' as const }
      : datasourceType.value === 'excel'
        ? { name: excelForm.name.trim() || 'Excel 数据源', type: 'Excel' as const }
        : { name: dbForm.name.trim() || 'MySQL 数据源', type: 'MySQL' as const };

  emit('save', {
    id: `${item.type}-${Date.now()}`,
    name: item.name,
    type: item.type,
    status: 'Mock 已保存'
  });
}
</script>

<style scoped>
.datasource-workspace {
  display: grid;
  grid-template-rows: auto minmax(0, 1fr) auto;
  height: 100%;
  background: transparent;
}

.datasource-workspace__topbar {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
  padding: 0.1rem 0 0.8rem;
}

.datasource-workspace__eyebrow {
  margin: 0;
  color: rgba(77, 63, 49, 0.62);
  font-size: 0.75rem;
  letter-spacing: 0.18em;
  text-transform: uppercase;
}

.datasource-workspace__topbar h1 {
  margin: 0.3rem 0 0;
  color: #231f1a;
  font-size: 1.15rem;
}

.datasource-workspace__content {
  min-height: 0;
  overflow: auto;
  display: grid;
  align-content: start;
  gap: 0.85rem;
}

.datasource-workspace__types {
  display: inline-flex;
  gap: 0.45rem;
  flex-wrap: wrap;
}

.datasource-workspace__type {
  padding: 0.42rem 0.78rem;
  border: 1px solid rgba(49, 40, 31, 0.08);
  border-radius: 999px;
  background: #ffffff;
  color: #463a2f;
  cursor: pointer;
}

.datasource-workspace__type--active {
  background: rgba(39, 33, 27, 0.1);
  border-color: rgba(39, 33, 27, 0.18);
  color: #2f2821;
}

.datasource-workspace__panel {
  display: grid;
  gap: 0.75rem;
  padding: 0.9rem;
  border: 1px solid rgba(49, 40, 31, 0.08);
  border-radius: 18px;
  background: #ffffff;
}

.datasource-workspace__field,
.datasource-workspace__field-grid {
  display: grid;
  gap: 0.38rem;
}

.datasource-workspace__field-grid {
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 0.65rem;
}

.datasource-workspace__field label {
  color: #4a3f34;
  font-size: 0.82rem;
}

.datasource-workspace__footer {
  display: flex;
  justify-content: flex-end;
  gap: 0.7rem;
  padding-top: 0.8rem;
}

@media (max-width: 900px) {
  .datasource-workspace__field-grid {
    grid-template-columns: 1fr;
  }
}
</style>
