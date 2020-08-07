import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import axios from 'axios'
import store from './store/store';
import service from './utils/request';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
import './assets/css/icon.css';
import './components/common/directives';
import 'babel-polyfill';
import qs from 'qs';


Vue.prototype.$qs = qs;
Vue.prototype.$axios = axios
Vue.prototype.$service = service
Vue.config.productionTip = false;

Vue.use(ElementUI, {
    size: 'small'
});


//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | vue-manage-system`;
    const role = sessionStorage.getItem('username');
    let token= sessionStorage.getItem('token');
    if(token||to.path === '/login') {
            next()
    }else if(!token){
        router.push({ path: '/login'});
        next()
    } else{
        next('/403')
    }
});

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');
