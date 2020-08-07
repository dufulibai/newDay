import Vue from 'vue';
import Router from 'vue-router';
import createStaForm from '../components/page/manageSta/createStaFormRoutes'
Vue.use(Router);

export default new Router({

    mode: 'history',
    routes: [
        {
            path: '/tWc',//测试websocket
            component: () => import(/* webpackChunkName: "login" */ '../entry2/websocket/webSocketIndex.vue'),
            meta: { title: 'websocket' }
        },

        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        // {
        //     path:'/',
        //     redirect:'/dashboard'
        // },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/dashboard',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/Dashboard.vue'),
                    meta: { title: '系统首页' },
                },
                {
                    name: 'manageSta',
                    path: '/manageSta',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/manageSta.vue'),
                    meta: { title: '场馆管理' }
                },
                {
                    name:'createStaForm',
                    path: '/createStaForm',
                    component: ()=>import("../components/page/manageSta/createStaForm.vue"),
                    meta: { title: '场馆管理' },
                },
                {
                    name:'staSpace',
                    path:'/staSpace',
                    component:()=>import("../components/page/manageSta/staSpace.vue"),
                    meta: { title: '场馆管理' },
                },
                {
                    path:'/users',
                    name:'users',
                    component:()=>import("../components/page/usersManage.vue"),
                    meta:{title:'用户信息'}
                },
                {
                    path:'/orders',
                    name:'orders',
                    component:()=>import("../components/page/ordersManage.vue"),
                    meta:{title:'订单管理'}
                },
                {
                    path:'/saleChart',
                    name:'saleChart',
                    component:()=>import("../components/page/sales.vue"),
                    meta:{title:'销售额图表'}
                },
            ]
        },
    ]
});
