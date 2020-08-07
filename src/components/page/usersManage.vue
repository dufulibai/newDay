<template>
    <div class="userMana">
        <div class="container">
            <div class="tableHeader">
                <span>用户名</span> <span>电话</span>
            </div>
            <div v-for="(item,index) in tableData" :key="index">
                <span>{{item.name}}</span>
                <span>{{item.phone}}</span>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: 'usersManage',
        data() {
            return {
                tableData: '',
                search: ''
            }
        },
        mounted(){
          this.getAllusers()
        },
        methods: {
          getAllusers(){
              let that = this
              this.$service.get('/sms/getAllUsers')
                  .then((data)=>{
                      that.tableData = data.data
                  }).catch((err)=>{
                      console.log(err)
              })
          }
        },
    };
</script>

<style scoped>
.userMana{
    width: 100%;
    min-height: 100%;
    background: #ffffff;
    padding: 20px;
}
.container{
    width: 50%;
    min-height: 40%;
    margin: 0 auto;
    margin-top: 10%;
}

.tableHeader span{
    display: inline-block;
    min-width: 300px;
    color: #000;
    font-size: 25px;
    font-weight: bold;
    border: 1px solid #ccc;
}
 span{
    display: inline-block;
    min-width: 300px;
    color: #000;
    border: 1px solid #ccc;
    padding: 10px;
}
</style>
