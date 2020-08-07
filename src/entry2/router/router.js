import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router);

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/front/frontLogin',
            name:'frontLogin',
            component: () => import('../components/login.vue'),
        },
        {
            path:'/front/payResult',
            name:'payResult',
            component:()=>import('../components/payResult.vue'),
            meta:{title:'payResult'}
        },

        {
            path: '/front/frontIndex',
            name: 'frontIndex',
            component:() => import('../components/frontIndex.vue'),
            children:[
                {
                    path:'/front/myInfo',
                    name:'myInfo',
                    component:()=>import('../components/MyInfo.vue'),
                    meta:{title:'myInfo'}
                },
                {
                    path:'/',
                    name:'frontAllSta',
                    component:() => import('../components/frontAllSta.vue')
                },
                {
                    path: '/front/frontStaInfo',
                    name: 'frontStaInfo',
                    component:() => import('../components/frontStaInfo.vue'),
                    children:[
                        {
                           path:'/front/spacePanel',
                            name:'spacePanel',
                            component:() => import('../components/spacePanel.vue')
                        }
                    ]
                }
            ]
        },
        {
            path:'/front/payQRcode',
            name:'payQRcode',
            component:()=>import('../components/payPage.vue')
        }

    ]
})
