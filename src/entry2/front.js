import Vue from 'vue'
import App from './front.vue'
import router from './router/router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
import '../assets/css/icon.css';
import '../components/common/directives';
import serviceFront from './utils/request'
import store from './store/store'
import {initWebSocket} from '../utils/websocket';

Vue.prototype.$serviceFront = serviceFront
// Vue.prototype.$stompClient = initWebSocket()
Vue.config.productionTip = false


Vue.use(ElementUI, {
    size: 'small'
});


router.beforeEach((to, from, next) => {
    const role = sessionStorage.getItem('username');
    let token= sessionStorage.getItem('token');
    if(token||to.path === '/front/frontLogin') {
        next()
    }else if(!token){
        router.push({ path: '/front/frontLogin' });
        next()
    }
});
new Vue({
    store,
    router,
    render: h => h(App)
}).$mount('#app')
