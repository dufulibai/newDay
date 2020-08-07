<template>
    <div>
        <div id="templatemo_header">
            <div id="site_title"><h1>体育馆预定系统</h1></div>
        </div> <!-- end of header -->

        <div id="templatemo_main">
            <div class="cbox_fw">
                <h2>标签</h2>
                <ul>
                    <li :class="clickTags.includes(item)?'acitve':''" v-for="(item,index) in tags" :key="index" @click="obtainTag(item)">{{item}}</li>
                </ul>
                <div class="cleaner"></div>
            </div>
            <div id="content">
                <div class="member_sta" v-for="(item,index) in filterstaInfo" :key="index">
<!--                    {{item}}-->
                   <h3 @click="goStaInfo(item)">{{item.sta_name}}</h3>
                    <label>标签：</label>
                    <label v-for="(item1,index1) in item.selectedTags" :key="index1">{{item1}}</label>
                </div>
                <div v-if="filterstaInfo.length<=0" style="text-align: center;"><label>暂无数据</label></div>
            </div>
            <div class="cleaner"></div>
        </div> <!-- end of main -->
        <div id="templatemo_main_bottom"></div>

        <div id="templatemo_footer">
            Copyright © 2048 <a href="#">Your Company Name</a> | Template from <a href="http://www.cssmoban.com" target="_parent" title="网站模板">网站模板</a>

            <div class="cleaner"></div>
        </div>
    </div>
</template>

<script>
    import {mapActions} from 'vuex'
    export default {
        name: 'allSta',
        data(){
            return{
                tags:[],
                clickTags:[],
                staInfo:[]
            }
        },
        computed:{
            filterstaInfo(){
                let _this = this
                if(_this.clickTags.length<=0){
                    return _this.staInfo
                }
                return this.staInfo.filter(function(item,index) {
                    return _this.clickTags.every(function(item1,index1) {
                        return item.selectedTags.includes(item1)
                    })
                })
            }
        },
        beforeRouteEnter(to,from,next){
            next(vm=>{
                // 查询标签
                vm.queryTags()
                vm. querySta()
            })
        },
        methods:{
            ...mapActions([
                'commitStaInfoTemp',
                'commitConsumRecord'
            ]),
            querySta(){
                let _this = this
                this.$serviceFront.get('/sms/getAllStaInfo')
                    .then(function(data) {
                        _this.staInfo = data.data
                    })
                    .catch(function(error) {
                        _this.$message.warning('场馆信息获取失败！')
                    })
            },
            queryTags(){
                let _this = this
                this.$serviceFront.get('/sms/getTags')
                    .then(function(data) {
                        if (data.data && data.data.length>0){
                            _this.tags = data.data
                        }
                    })
                    .catch(function(error) {
                        _this.$message.warning('标签获取失败！')
                    })
            },
            obtainTag(item){
                this.clickTags.includes(item)?this.clickTags.splice(this.clickTags.indexOf(item),1):this.clickTags.push(item)
            },
            goStaInfo(item){
                this.commitStaInfoTemp(item)
                this.$router.push({name:'frontStaInfo'})
            }
        }
    };
</script>

<style scoped>

</style>
