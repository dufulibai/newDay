<template>
    <div class="payResult" :style="htmlHeight">
        <div class="middle">
            <div class="wxpayLogo"></div>
            <div class="logoText">支付成功</div>
            <div class="money">￥ {{payAmount}}</div>
            <div class="finishBtn" @click="gotoIndex">完成</div>
        </div>
    </div>
</template>

<script>
    import {mapActions} from 'vuex'
    export default {
        name: 'payResult',
        data () {
            return {
                htmlHeight: {
                    height: '300px'
                }
            }
        },
        computed:{
          payAmount(){
              return this.$route.params.payAmount
          }
        },
        methods: {
            getHtmlHeight () {
                let htmlHeight = window.innerHeight + 'px'
                this.$set(this.htmlHeight, 'height', htmlHeight)
            },
            gotoIndex(){
                let checkedRecord = []
                this.commitCheckedRecord(checkedRecord)
                this.$router.push({name:'frontAllSta'})
            },
            ...mapActions([
                'commitCheckedRecord'
            ]),
        },
        mounted () {
            this.getHtmlHeight()
        }
    };
</script>

<style scoped>
  .payResult{
      width: 100%;
      background: #ffffff;
      overflow: hidden;
  }
    .logoText{
        font-size: 30px;
        color:#00a854;
        margin-top: 20px;
        text-align: center;
    }
  .wxpayLogo{
      width: 100%;
      height: 0;
      padding-top: 100%;
      background: url("images/wxlogo.jpg") no-repeat;
      background-position: center center;
      background-size: 100% 100%;

  }
    .middle{
        position: absolute;
        top: 20%;
        left: 45%;
        color: #000000;
        font-size: 18px;
        text-align: center;
    }
    .money{
        margin-top: 20px;
        font-size: 20px;
        font-weight: 800;
    }
    .finishBtn{
        width: 200px;
        height: 50px;
        text-align: center;
        line-height: 50px;
        color: #ffffff;
        font-weight: 800;
        background: rgba(52, 168, 34, 0.93);
        margin-top: 20px;
        cursor: pointer;
    }
  .finishBtn:hover{
      background: #2c9605;
  }
</style>
