
// var history = require('connect-history-api-fallback');
// var express = require('express');
// var app = express();
// app.use(history({
//     rewrites: [
//     { from: /^front\/.*$/, to: '/front.html'}
// ]
// }));

module.exports = {
    publicPath: '/',
    assetsDir: 'static',
    productionSourceMap: false,
    devServer: {
        host: '0.0.0.0',
        open: 'Chrome',
        https: false, // 协议
        overlay: {
            warnings: true,
            errors: true
        },
        port: 8081,
        proxy: {
            '/sms': {
                target: 'http://localhost:8000',
                changeOrigin: true,
                pathRewrite: {
                    '^/sms': '' // rewrite path
                }
            },
            'addons/pay/api': {
                target: 'http://localhost:7001', //API服务器的地址
                ws: true,  //代理websockets
                changeOrigin: true, // 虚拟的站点需要更管origin
                // pathRewrite: {   //重写路径 比如'/api/aaa/ccc'重写为'/aaa/ccc'
                //     '^/api': ''
                // }
            }
        }
    },
    pages:{
       index:{
           entry:'src/main.js',
           template: 'public/index.html',
           filename: 'index.html',
           chunks: ['chunk-vendors', 'chunk-common', 'index']
       },
        front:{
           entry: 'src/entry2/front.js',
           template: 'src/entry2/front.html',
           filename:'front.html',
           chunks: ['chunk-vendors', 'chunk-common', 'front']
       },
    },
    configureWebpack: {
        devServer: {
            historyApiFallback: {
                verbose: true,
                rewrites: [
                        { from: /^\/front\/.*$/, to: '/front.html' },
                ]
            }
        }
    }
}




