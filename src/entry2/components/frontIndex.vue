<template>
    <div id="templatemo_wrapper">

        <span class="bg_top"></span>
        <span class="bg_bottom"></span>

        <div id="templatemo_menu">
            <ul>
                <li :class="{'current':cur==0}" @click="goIndex"><a>首页</a></li>
                <li :class="{'current':cur==3}"><a @click="goOrder">我的</a></li>
                <li @click="loginout"><a>退出</a></li>
            </ul>
        </div> <!-- end of menu -->

        <router-view/>
    </div>
</template>

<script>
    import $ from 'jquery'
    export default {
        name: 'frontIndex',
        data(){
            return{
                cur:''
            }
        },
        mounted(){
            console.log(this.$route)
            switch (this.$route.name) {
                case 'frontAllSta':
                    this.cur = 0;
                    break;
                case 'myInfo':
                    this.cur = 3;
                    break;
            }
        },
        methods:{
            goIndex(){
                this.cur = 0
                this.$router.push({ name: 'frontAllSta'})
            },
            goOrder(){
                this.cur = 3
                this.$router.push({ name: 'myInfo'})
            },
            loginout() {
                console.log('退出')
                    sessionStorage.removeItem('username');
                    sessionStorage.removeItem('token');
                    this.$router.push('/front/frontLogin');
            },
        }
    };
</script>

<style scoped>
@import "templatemo_style.css";
</style>
