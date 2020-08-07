<template>
    <div class="scheduleContaniner">
        <el-form  :model="scheduleData" label-width="10rem">
            <el-form-item label="上线时间（年月日）">
                <el-date-picker
                        v-model="value1"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="开始年月日"
                        end-placeholder="结束年月日">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="开始营业日（每周）">
                <el-col :span="11">
                    <el-select v-model="getWeeks.getStartWeek" placeholder="请选择">
                        <el-option
                                v-for="item in weeks"
                                :key="item"
                                :label="item"
                                :value="item">
                        </el-option>
                    </el-select>
                </el-col>
                <el-col class="line" :span="2">-</el-col>
                <el-col :span="11">
                    <el-select v-model="getWeeks.getEndWeek" placeholder="请选择">
                        <el-option
                                v-for="item in weeks"
                                :key="item"
                                :label="item"
                                :value="item">
                        </el-option>
                    </el-select>
                </el-col>
            </el-form-item>

            <el-form-item label="营业时间（每天）">
                <el-input placeholder="请输入起始时间" v-model="input1">
                    <template slot="prepend">起始时间(比如：08:30)</template>
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-input placeholder="请输入步长" v-model="input1">
                    <template slot="prepend">步长(比如：00:15)</template>
                </el-input>
            </el-form-item>
            <el-form-item>
                <el-input placeholder="请输入结束时间" v-model="input1">
                    <template slot="prepend">结束时间(比如：18:30)</template>
                </el-input>
            </el-form-item>
            <div style="margin-top: 50px;">
                <span style="color: #964e7d;font-size: 16px;">请选择使用此时刻表的对象 :</span>
                <span style="margin-left: 20px">
                            <el-cascader
                                    :options="options"
                                    :props="props"
                                    clearable></el-cascader>
                        </span>
            </div>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: 'createSchedule',
        data() {
            return {
                scheduleData:[],
                getWeeks:{
                    getStartWeek:'',
                    getEndWeek:''
                },
                weeks:['周一','周二','周三','周四','周五','周六','周日'],
                props: { multiple: true },
                options: [{
                    value: 1,
                    label: '东南',
                    children: [{
                        value: 2,
                        label: '上海',
                        children: [
                            { value: 3, label: '普陀' },
                            { value: 4, label: '黄埔' },
                            { value: 5, label: '徐汇' }
                        ]
                    }, {
                        value: 7,
                        label: '江苏',
                        children: [
                            { value: 8, label: '南京' },
                            { value: 9, label: '苏州' },
                            { value: 10, label: '无锡' }
                        ]
                    }, {
                        value: 12,
                        label: '浙江',
                        children: [
                            { value: 13, label: '杭州' },
                            { value: 14, label: '宁波' },
                            { value: 15, label: '嘉兴' }
                        ]
                    }]
                }, {
                    value: 17,
                    label: '西北',
                    children: [{
                        value: 18,
                        label: '陕西',
                        children: [
                            { value: 19, label: '西安' },
                            { value: 20, label: '延安' }
                        ]
                    }, {
                        value: 21,
                        label: '新疆维吾尔族自治区',
                        children: [
                            { value: 22, label: '乌鲁木齐' },
                            { value: 23, label: '克拉玛依' }
                        ]
                    }]
                }]
            }
        }
    };
</script>

<style scoped>

</style>
