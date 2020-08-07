import axios from 'axios'
import router from '../router/router';
import { Message } from 'element-ui';

const serviceFront = axios.create({
    // process.env.NODE_ENV === 'development' 来判断是否开发环境
    // easy-mock服务挂了，暂时不使用了
    // baseURL: 'https://www.easy-mock.com/mock/592501a391470c0ac1fab128',
    timeout: 5000
});

serviceFront.interceptors.request.use(
    config => {
        // 这里的config包含每次请求的内容
        let url = config.url;
        if (url.indexOf('login')<0){
            let token = window.sessionStorage.getItem('token')
            if (token) {
                // 添加headers
                config.headers.authorization = `Bearer${token}`;
                config.headers['content-type'] = 'application/json;charset=utf-8';
            }
        }
        return config;
    },
    error => {
        return Promise.reject();
    }
);

serviceFront.interceptors.response.use(
    response => {
        if (response.status === 200) {
     if (response.data.hasOwnProperty('token')){
         sessionStorage.setItem('token',response.data.token)
     } else if (response.data.hasOwnProperty('invalidToken')){
         sessionStorage.setItem('token','')
         sessionStorage.setItem('username','')
         router.push('/front/frontLogin')
         Message.warning('登录失败！请重新登陆')
     }
            return response;
        } else {
            Promise.reject();
        }
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);
export default serviceFront;
