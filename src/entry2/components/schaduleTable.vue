<template>
    <div id="cc" class="schaduleTable">
        <ul>
            <li class="hengxiang" v-for="(item,index) in getSpace" :key="index">
                <div style="text-align: center;color: black">{{item.spaName}} <i>({{'￥'+item.scha_price +'/'+item.scha_priceTime+'时'}})</i></div>
                <scrollSlot :data="{select:'2'}">
                <ul id="liul">
                    <li :class="getActive.includes(
                        item.staNo+''+''+item.tag+''+scrollTimeDatesItem.year+''+scrollTimeDatesItem.month+''
                        +scrollTimeDatesItem.day+''+item.spaNo+''+item1
                    ) &&!disabledNode.includes((item.staNo+''+item.tag+''+scrollTimeDatesItem.year+''+scrollTimeDatesItem.month+''
                        +scrollTimeDatesItem.day+''+item.spaNo+''+item1).replace(new RegExp(/(:)/g),''))?'active':disabledNode.includes((item.staNo+''+item.tag+''+scrollTimeDatesItem.year+''+scrollTimeDatesItem.month+''
                        +scrollTimeDatesItem.day+''+item.spaNo+''+item1).replace(new RegExp(/(:)/g),''))?'disabled':''" @click="!disabledNode.includes((item.staNo+''+item.tag+''+scrollTimeDatesItem.year+''+scrollTimeDatesItem.month+''
                        +scrollTimeDatesItem.day+''+item.spaNo+''+item1).replace(new RegExp(/(:)/g),''))?getsmallTime(item1,item,index,index1):''" class="zongxiang" v-for="(item1,index1) in item.ptimes" :key="index1">{{item1}}</li>

                </ul>
                </scrollSlot>
            </li>
        </ul>
    </div>
</template>

<script>
    import {mapState,mapActions} from 'vuex'
    import scrollSlot from './scrollSlot';
    import $ from 'jquery'
    export default {
        name: 'schaduleTable',
        data(){
            return{
                checked:{},
                orders:'',
                disabledNode:[]
            }
        },
        components:{
            scrollSlot,
        },

        computed:{
            getActive(){
                let x = []
                this.checkedRecord.forEach(function(item) {
                    x.push(item.staNo+''+item.tag+''+item.year+''+item.month+''+item.day+''+item.spaNo+''+item.timeNode)
                })
                return x
            },
            ...mapState({
                'spaceTempBysta':state => state.spaceTempBysta,
                'spaceBystaNoTemp':state => state.spaceBystaNoTemp,
                'scrollTimeDatesItem':state => state.scrollTimeDatesItem,
                'checkedRecord':state => state.checkedRecord,
            }),
            getSpace(){
                let o = {'tt':this.scrollTimeDatesItem.schaTable}
                let o1 = JSON.parse(JSON.stringify(o))
                return o1.tt
            }
        },
        mounted(){
            this.getOrders()
        },
        methods:{
            getDisabledOrder(){
                let that = this
                this.orders.forEach(function(item) {
                    let id = (item.staNo+""+item.tag+""+item.year+""+item.month+""+item.day+""+item.spaNo+""+item.timeNode).replace(new RegExp(/(:)/g),'')
                    that.disabledNode.push(id)
                })

            },
            getOrders(){
                let that = this
                this.$serviceFront.get("/sms/getAllOrders")
                    .then(function(data) {
                        if (data.data){
                            that.orders = data.data
                            that.getDisabledOrder()
                        }
                    }).catch(function(error) {

                })
            },
            ...mapActions([
                'commitCheckedRecord'
            ]),
            wrapperRecordData(item1,item){
                let {schaTable,...date1} = this.scrollTimeDatesItem
                let {date,...date2} = date1
                let {ptimes,...baseInfo} = item
                let checkedT = {}
                Object.assign(checkedT,baseInfo,date2)
                checkedT.timeNode = item1
                return checkedT
            },
            getsmallTime(item1,item){
                let checkedT = this.wrapperRecordData(item1,item)
                let checkedRecord = this.handleChecked(checkedT)
                this.commitCheckedRecord(checkedRecord)
            },
            handleChecked(data){
                let checkedRecord
                if(this.checkedRecord.length>0){
                    checkedRecord = JSON.parse(JSON.stringify(this.checkedRecord));
                }else {
                    checkedRecord = []
                }
                if (data){
                    if (checkedRecord.length>0){
                        var indeof;
                        let isfind = checkedRecord.every(function(v,index){
                            for(let key in v){
                                if(v[key] != data[key]){
                                    return true
                                }
                            }
                            indeof = index//返回已有项的下标
                        })
                        if (isfind){
                            checkedRecord.push(data)
                        }else{
                            checkedRecord.splice(indeof,1)
                        }
                    }else{
                        checkedRecord.push(data)
                    }
                    return checkedRecord
                }
            }
        }
    };
</script>

<style scoped>
    @import "templatemo_style.css";
    .schaduleTable{
        width: 90%;
    }
    .hengxiang{
        display: inline-block;
        margin-bottom: 30px;
    }
.zongxiang{
    width: 300px;
    min-height: 1rem;
    padding: 5px;
    margin: 5px;
    cursor: pointer;
}
    .zongxiang:hover{
        background: rgba(95, 44, 53, 0.3);
    }
    .active{
        background: #5f2712;
        color: #fff;
    }
    .disabled{
        background: rgba(87, 95, 89, 0.31);
        color: #ffffff;
        cursor: auto;
    }
    .disabled:hover{
        background: rgba(87, 95, 89, 0.31);
        color: #ffffff;
    }
</style>
