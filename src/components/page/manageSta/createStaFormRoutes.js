// 导出该模块路由
export default [
    {
        title:"创建场馆信息表",
        name: "createStaForm",
        path: 'createStaForm/:hasTag',
        component: ()=>import("./createStaForm.vue"),
        meta: { title: '创建场馆' },
    }
];
