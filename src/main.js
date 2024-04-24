import { createApp } from 'vue'
import router from './router'
import App from './App.vue'
import mitt from 'mitt'

const emitter = mitt()
const app = createApp(App)

app.config.globalProperties.emitter = emitter
app.use(router)
app.mount('#app')
