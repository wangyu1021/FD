import Vue from 'vue'
import App from './App'
import router from './router'
import $ from "jquery";
import ElementUI from "element-ui";
import 'element-ui/lib/theme-chalk/index.css'
import echarts from 'echarts'
import param from './components/param'
import store from './store'

Vue.prototype.param = param
Vue.prototype.$echarts = echarts
Vue.config.productionTip = false
Vue.use(ElementUI);
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
})
