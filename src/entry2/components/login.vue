<template>
    <div id="login_wrapper">
        <div class="login_center">
            <div class="login_header">
                <div class="templatemo_menu">
                    <ul>
                        <li @click="()=>{islogin=true;isRegister=false}" :class="islogin?'activeItem':''">登录</li>
                        <li @click="()=>{islogin=false;isRegister=true}" :class="isRegister?'activeItem':''">注册</li>
                    </ul>
                </div>
            </div>
            <div class="login_main">
                <el-form v-show="islogin" :model="paramL" :rules="rules" ref="login" label-width="0px" class="ms-content">
                    <el-form-item prop="username">
                        <el-input v-model="paramL.username" placeholder="username">
                            <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input
                                type="password"
                                placeholder="password"
                                v-model="paramL.password"
                                @keyup.enter.native="submitForm()"
                        >
                            <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                        </el-input>
                    </el-form-item>
                    <div class="login-btn">
                        <el-button  class="sub_btn" @click="submitForm()"></el-button>
                    </div>
                </el-form>
                <el-form v-show="isRegister" :model="paramR" :rules="rules" ref="register" label-width="0px" class="ms-content">
                    <el-form-item prop="username">
                        <el-input v-model="paramR.username" placeholder="username">
                            <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input
                                type="password"
                                placeholder="password"
                                v-model="paramR.password"
                                @keyup.enter.native="submitRegister()"
                        >
                            <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                        </el-input>
                    </el-form-item>
                    <div class="login-btn">
                        <el-button  class="sub_btnR" @click="submitRegister()">注册</el-button>
                    </div>
                </el-form>
            </div>
            <div class="login_bottom"></div>
        </div>

    </div>
</template>

<script>
    export default {
        name: 'login.vue',
        data(){
            return {
                islogin:true,
                isRegister:false,
                paramL: {
                    username: 'user1',
                    password: '456',
                },
                paramR: {
                    username: 'user1',
                    password: '456',
                },
                rules: {
                    username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
                },
            }
        },
        methods:{
            submitForm(){
                const self = this;
                this.$refs.login.validate(valid => {
                    if (valid) {
                        let username = this.paramL.username
                        this.$serviceFront.post('/sms/login',{
                                "username":this.paramL.username,
                                "password":this.paramL.password
                            }
                        ).then(function (data) {
                            if (sessionStorage.getItem('token')){
                                sessionStorage.setItem('username',username)
                                self.$router.push('/front/frontIndex')
                                self.$message.success('登录成功');
                            }else if (data.data.hasOwnProperty('invalidToken')){
                                self.$message.error('用户名或密码错误');
                            }
                        }).catch(function (error) {
                        })

                    } else {
                        this.$message.error('请输入账号和密码');
                        return false;
                    }
                });
            },
            submitRegister(){
                const self = this;
                this.$refs.register.validate(valid => {
                    if (valid) {
                        let username = this.paramR.username
                        this.$serviceFront.post('/sms/register',{
                                "username":this.paramR.username,
                                "password":this.paramR.password
                            }
                        ).then(function (data) {
                                self.$message.success('注册成功');
                        }).catch(function (error) {
                            self.$message.success('注册失败');
                        })

                    } else {
                        this.$message.error('请输入账号和密码');
                        return false;
                    }
                });
            }
        }
    };
</script>

<style scoped>
    #login_wrapper {
        position:fixed;
        top: 0;
        left: 0;
        width:100%;
        height:100%;
        z-index:-10;
        background: url(images/templatemo_bg_top.png) no-repeat fixed center 0;
        background-size: cover;
    }
    .login_center{
        width: 50%;
        height: 50%;
        margin: 10% auto;
    }
    .login_header{
        width: 100%;
        height: 40%;
        font-size: 0;
        vertical-align: bottom;
        background:url("images/templatemo_header.png") no-repeat;
        background-size: cover;
    }
    .ms-content {
        padding:0 30%;
    }
    .login_main{
        width: 100%;
        height: 40%;
        font-size: 0;
        vertical-align: bottom;
        background:url("images/templatemo_main.png") no-repeat;
        background-size: cover;
    }
    .login_bottom{
        width: 100%;
        height: 40%;
        font-size: 0;
        vertical-align: bottom;
        background:url("images/templatemo_main_bottom.png") no-repeat;
        background-size: cover;
    }
    .sub_btn, .sub_btnR{
        float: right;
        display: block;
        height: 32px;
        width: 87px;
        cursor: pointer;
        font-size: 12px;
        text-align: center;
        vertical-align: bottom;
        white-space: pre;
        color: #000;
        border: none;

    }
    .sub_btn{
        background: url(images/templatemo_login_btn.png)
    }

    .sub_btnR{
        color: #fff;
        background: #62260e;
        border-radius: 10px;
    }
    .templatemo_menu{
        font-size: 16px;
        font-style: italic;
        color: #62260e;
        text-align: center;
        text-decoration: none;
        font-weight: 700;
        padding-top: 8%;
    }
    .templatemo_menu li{
        display: inline-block;
        padding:0 5%;
        cursor: pointer;
    }
    .templatemo_menu li:first-child{
        border-right: 2px solid #62260e;
    }
    /*.templatemo_menu li:hover{*/
    /*    color: #620513;*/
    /*    text-decoration: underline;*/
    /*}*/
    .activeItem{
        color: #d30717;
        text-decoration: underline;
    }

</style>
