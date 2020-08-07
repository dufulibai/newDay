<template>
    <div>
        <shop-cart></shop-cart>
        <div id="templatemo_header">
            <div id="site_title">
                <h1>{{staInfoTemp.sta_name}}</h1>

            </div>

        </div>
        <div id="templatemo_main">
            <div class="main_base">
                <p>
                    <label>地址:</label>
                    <label>{{staInfoTemp.sta_addr}}</label>
                </p>
                <p>
                    <label>联系电话：</label>
                    <label>{{staInfoTemp.sta_phone}}</label>
                </p>
                <p>
                    <label>营业时间：</label>
                    <label>{{staInfoTemp.sta_startDayTime}}-{{staInfoTemp.sta_endDayTime}}</label>
                </p>
                <p>
                    <label>公告：</label>
                    <label>{{staInfoTemp.sta_notice}}</label>
                </p>
                <div class="cleaner"></div>
            </div>
            <div class="main_tags">
                <span @click="goSpace(item)" :class="cursorTag==item?'spaTagactive':''" v-for="(item,index) in staInfoTemp.selectedTags" :key="index">{{item}}</span>
            </div>
            <router-view/>
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
    import { mapState,mapActions } from 'vuex'
    import fun_date from '../utils/showRent21Day';
    import secondsToHhMm from '../utils/secondsFormat'
    import shopCart from './shopCart';
    export default {
        name: 'frontStaInfo',
        data(){
            return{
              cursorTag:'',
              allspaceScha:''
            }
        },
        components:{
            shopCart
        },
        computed:{
            ...mapState({
                'staInfoTemp': state => state.staInfoTemp,
                'spaceBystaNoTemp': state => state.spaceBystaNoTemp,
                // consumRecord: state => state.consumRecord
            })
        },
        beforeRouteEnter(to,from,next){
            next(vm=>{
                // 查询标签
                vm.querySpaInfoBySta()
            })
        },
        mounted(){
            this.cursorTag = this.staInfoTemp.selectedTags[0]
            this.goSpace(this.cursorTag)
        },
        methods:{
            ...mapActions([
                'commitSpaceBystaNoTemp',
                'commitSpaceTempBysta',
                'commitConsumRecord'
            ]),
            querySpaInfoBySta(){//查询场地及时刻表
                let _this = this
                this.$serviceFront.post('/sms/getAllSpaBySta',
                    JSON.stringify({staNo:this.staInfoTemp.staNo}))
                    .then(function(data) {
                        _this.allspaceScha = data.data
                        let record = []
                        for(let key of _this.staInfoTemp.selectedTags){
                            let obj = {}
                            obj.staNo = _this.staInfoTemp.staNo
                            obj.tag = key
                            let dates = []
                            for(let i = 0;i<=21;i++){
                                let ob = fun_date(i)
                                ob.schaTable = _this.schaduleByTag(key)
                                dates.push(ob)
                            }
                            obj.dates = dates
                            record.push(obj)
                        }
                        _this.commitConsumRecord(record)
                    })
                    .catch(function(error) {

                    })
            },
            schaduleByTag(tag){
                let _this = this
                let arr =  this.allspaceScha.filter(function(item) {
                    return item.tag == tag
                })
                arr.forEach( function(item) {
                    item.ptimes = []
                    let startTimes = item.scha_startTime.split(':')
                    let shours = parseInt(startTimes[0])
                    let smin = parseInt(startTimes[1])
                    let startToSeconds = shours*60*60 + smin*60

                    let priceSeconds = item.scha_priceTime*60*60

                    let endTimes = item.scha_endTime.split(':')
                    let ehours = parseInt(endTimes[0])
                    let emin = parseInt(endTimes[1])
                    let endToSeconds = ehours*60*60 + emin*60
                    for(let i = startToSeconds; i<endToSeconds; ){
                        let t = i+priceSeconds
                        let t1 = secondsToHhMm(i)
                        let t2 = secondsToHhMm(t)
                        item.ptimes.push(t1+'-'+t2)
                        i += priceSeconds
                    }

                })
                return arr
            },
            goSpace(item){
                this.cursorTag = item
                let params = {}
                params.tag = item
                params.staNo = this.staInfoTemp.staNo
                this.commitSpaceTempBysta(params)
                this.$router.push({name:'spacePanel'})
            }
        }

    };
</script>

<style scoped>

</style>
