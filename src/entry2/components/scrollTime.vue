<template>
    <div class="outer-container">
        <div class="inner-container">
            <div class="content">
                <ul>
                    <li :class="checked==(item.month+'-'+item.day)?'checked':''"  v-for="(item,index) in getDates[0].dates" :key="index" @click="scrollTimeLi(item)">
                       <p>{{item.hanWeekDay}}</p>
                       <p>{{item.month}}-{{item.day}}</p>
                        <p></p>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapActions,mapState } from 'vuex'
    export default {
        name: 'scrollTime',
       data(){
         return{
             checked:''
         }
       },
        watch:{
            getDates:{//深度监听，可监听到对象、数组的变化
                handler(val, oldVal){
                    this.commitScrollTimeDatesItem(val[0].dates[0])
                },
                deep:true //true 深度监听
            }
        },
        mounted(){
            this.checked = this.getDates[0].dates[0].month+'-'+this.getDates[0].dates[0].day
            this.commitScrollTimeDatesItem(this.getDates[0].dates[0])
        },
        computed:{
          ...mapState({
              'consumRecord':state=> state.consumRecord,
              'spaceTempBysta':state => state.spaceTempBysta
          }),
            getDates(){
              let _this = this
              let arr = this.consumRecord.filter(function(item) {
                  return item.tag == _this.spaceTempBysta.tag
              })
                return arr
            }
        },
        methods: {
            ...mapActions([
                'commitScrollTimeDatesItem'
            ]),
            scrollTimeLi(item,index){
                this.checked = item.month+'-'+item.day
                this.commitScrollTimeDatesItem(item)
            }
        }
    };
</script>

<style scoped>
    .outer-container,.content {
        width: 60vw; height: 5rem;
    }
    .outer-container {
        overflow: hidden;
        transform: translateX(25%);
    }
    .inner-container {
        width: 30%;
        height:7rem;
        overflow-x: scroll;
        overflow-y: hidden;
    }
    .content ul{
        list-style: none;
    }
    .content ul li{
        display: inline-block;
        margin-right: 1rem;
        cursor: pointer;
        padding: 5px;
    }
    .checked{
        background:#5f2712;
        color: #fff;
    }
    .table{
        min-height:7rem ;
    }
</style>
