import { createApp } from 'vue';
import { createPinia } from 'pinia';
import {
  create,
  NButton,
  NCard,
  NConfigProvider,
  NIcon,
  NInput,
  NLayout,
  NLayoutContent,
  NLayoutSider,
  NScrollbar,
  NSpace,
  NTag,
  NText
} from 'naive-ui';

import App from './App.vue';
import router from './router';
import './styles/global.css';

const app = createApp(App);
const naive = create({
  components: [
    NButton,
    NCard,
    NConfigProvider,
    NIcon,
    NInput,
    NLayout,
    NLayoutContent,
    NLayoutSider,
    NScrollbar,
    NSpace,
    NTag,
    NText
  ]
});

app.use(createPinia());
app.use(router);
app.use(naive);
app.mount('#app');
