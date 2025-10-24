import { createApp } from 'vue'
import { createRouter, createWebHistory } from 'vue-router'
import App from './App.vue'
import HomePage from './views/HomePage.vue'
import AnalyzePage from './views/AnalyzePage.vue'
import i18n from './i18n.js'
import './style.css'

const routes = [
  { path: '/', component: HomePage },
  { path: '/analyze', component: AnalyzePage }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

createApp(App).use(router).use(i18n).mount('#app')
