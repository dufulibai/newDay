<template>
    <div id="createSta">
        <el-row :gutter="10">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="box-card" style="width:20%;height: 200px;">
                    <div class="createSta" @click="createSta()" style="cursor: pointer;"><i class="el-icon-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;创建场馆</div>
                </el-card>
            </el-col>

        </el-row>
    </div>
</template>

<script>
    export default {
        name: 'createSta',
        methods: {
            createSta() {
                this.$router.push({name:'createStaForm',params: { hasTag: 'manageSta' }})
            }
        },
    };
</script>

<style scoped>
    .createSta{
        width: 100%;
        height: 10rem;
        cursor: pointer;
        border: 1px dashed #00a854;
        text-align: center;
        line-height: 10rem;
        color: #00a854;
        font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
        font-size: larger;
    }
</style>
