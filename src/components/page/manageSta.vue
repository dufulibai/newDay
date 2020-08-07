<template>
    <div id="manageSta">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-s-management"></i> 场馆</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-row>
                <el-col :span="5">
                    <el-card style="height: 200px;margin-bottom: 1rem;">
                       <div class="createSta" @click="createSta()"><i class="el-icon-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;创建场馆</div>
                    </el-card>
               </el-col>
                <el-col :offset="3" :span="1" style="color: red;font-size: 1rem;font-weight: 800;
                vertical-align: center;margin-top:3rem; ">过滤:</el-col>
                <el-col :span="6" style="margin-top:3rem;">
                    <el-input v-model="search" placeholder="请输入内容"></el-input>
                </el-col>
            </el-row>
            <div v-if="listStaInfo.length>0">
                <el-row :gutter="10" v-for="(item,index) in searchListStaInfo" :key="index">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-card class="listCard">
                            <span @click="goSta(item)">场馆名称:{{item.sta_name}}</span><span>创建时间:{{item.sta_createTime}}</span>
                            <span style="float: right;" @click="deleteStaInfo(item)">删除</span>
                        </el-card>
                    </el-col>
                </el-row>
            </div>
        </div>
    </div>
</template>

<script>
    import bus from '../common/bus';
    import {mapActions} from 'vuex'
    export default {
        name: 'manageSta',
        data(){
            return{
                search:'',
                listStaInfo:[]
            }
        },
        computed:{
            searchListStaInfo(){
                let _this = this
                if(this.listStaInfo&&this.listStaInfo.length>0){
                    if(_this.search){
                        return this.listStaInfo.filter(function(item) {
                            return (item.sta_name.includes(_this.search) || item.sta_createTime.includes(_this.search))
                        })
                    }
                }

                return _this.listStaInfo
            }
        },
        beforeRouteEnter (to, from, next) {
            next(vm => {
                //查询已有标签
                vm.queryAllStaInfo()
            })
        },
        methods: {
            //删除场馆信息
            deleteStaInfo(item){
                let _this = this
                this.$service.post('/sms/deleteSta',JSON.stringify({staNo:item.staNo}))
                    .then(function(data) {
                        _this.queryAllStaInfo()
                        _this.$message.success('删除成功！')
                    })
                    .catch(function(error) {
                        _this.$message.warning('删除失败！')
                    })
            },
            queryAllStaInfo(){
                let _this = this
                this.$service.get('/sms/getAllStaInfo')
                    .then(function (data) {
                        _this.listStaInfo = data.data;
                    }).catch(function (error) {
                })
            },
            ...mapActions([
                'commitSData',
            ]),
            createSta() {
                let changePath =  {
                    name:'createStaForm',
                    path: '/createStaForm',
                    localName: this.$route.fullPath}
                bus.$emit('changePath', changePath);
                this.$router.push({name:'createStaForm'})
            },
            goSta(item){
                let changePath =  {
                    name:'staSpace',
                    path: '/staSpace',
                    localName: this.$route.fullPath}
                bus.$emit('changePath', changePath);
                this.commitSData(item);
                this.$router.push({name:'staSpace'})
            },
        },
    };
</script>

<style lang="less" scoped>
.createSta{
    width: 100%;
    height: 10rem;
    border: 1px dashed #00a854;
    text-align: center;
    line-height: 10rem;
    color: #00a854;
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    font-size: larger;
}
    .listCard{
        width:60%;
        height:4rem;
        margin: 0.1rem auto;
        color: black;
        cursor: pointer;
    }
    .listCard span{
        margin: 0 2rem;
        color: #20a0ff;
    }
.listCard span:first-child:hover,.listCard span:last-child:hover{
     text-decoration: underline;
     color: #ff4d51;
}
</style>
