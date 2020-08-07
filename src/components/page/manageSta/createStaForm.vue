<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 场馆管理
                </el-breadcrumb-item>
                <el-breadcrumb-item>创建场馆信息表单</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">

            <div>
            <div class="form-box1">
                <el-form ref="staInfor" :rules="rules"  :model="staInfo" label-width="6rem">
                    <el-form-item label="场馆名称" prop="sta_name">
                        <el-input v-model="staInfo.sta_name" class="shadow"></el-input>
                    </el-form-item>
                    <el-form-item label="详细地址" prop="sta_addr">
                        <el-input v-model="staInfo.sta_addr" class="shadow"></el-input>
                    </el-form-item>
                    <el-form-item label="联系电话" prop="sta_phone">
                        <el-input v-model="staInfo.sta_phone" class="shadow"></el-input>
                    </el-form-item>
                    <el-form-item label="营业时间" prop="sta_opentime">
                        <el-time-picker
                                is-range
                                v-model="staInfo.sta_opentime"
                                range-separator="至"
                                start-placeholder="开始时间"
                                end-placeholder="结束时间"
                                placeholder="选择时间范围"
                                class="shadow">
                        </el-time-picker>
                    </el-form-item>
                    <el-form-item label="场馆公告">
                        <el-input type="textarea" rows="5" v-model="staInfo.sta_notice" class="shadow"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-card class="box-card">
                            <div slot="header" class="clearfix">
                                <span>请将选择的标签移到这儿~</span>
                            </div>
                            <div id="selectedTags" class="leftBox" @drop="drop($event)" @dragover="allowDrop($event)">
                                <ul>
                                    <li v-for="(item,index) in selectedTags" class="selectedTags" @dragstart="drag($event)" draggable="true">
                                        <el-tag
                                                :key="item"
                                                :disable-transitions="false"
                                                class="elTag"
                                                closable
                                                @close="handleCloseSele(item)"
                                                :type="getColor(index)"
                                        >{{item}}
                                        </el-tag>
                                    </li>
                                </ul>
                            </div>
                        </el-card>
                    </el-form-item>
                    <el-form-item>
                        <el-card class="box-card">
                            <div slot="header" class="clearfix">
                                <span>标签</span>
                                <el-input
                                        class="input-new-tag"
                                        v-if="inputVisible"
                                        v-model="inputValue"
                                        ref="saveTagInput"
                                        @keyup.enter.native="handleInputConfirm"
                                        @blur="handleInputConfirm"
                                >
                                </el-input>
                                <el-button v-else class="button-new-tag" @click="showInput">+ New Tag</el-button>
                            </div>
                            <div class="rightBox" id="tag" @drop="drop($event)" @dragover="allowDrop($event)">
                                <ul>
                                    <li v-for="(item,index) in tag" class="tag" @dragstart="drag($event)" draggable="true">
                                        <el-tag
                                                :key="item"
                                                closable
                                                :disable-transitions="false"
                                                class="elTag"
                                                @close="handleClose(item)"
                                                :type="getColor(index)"
                                        >{{item}}
                                        </el-tag>
                                    </li>
                                </ul>
                            </div>
                        </el-card>
                    </el-form-item>
                </el-form>
            </div>
            </div>

            <div class="btnGroup" >
                <el-row>
                    <el-button type="primary" size="medium" @click="submitData('staInfor')">提交</el-button>
                    <el-button size="medium" @click="back">返回</el-button>
                </el-row>
            </div>
        </div>
    </div>
</template>

<script>
    import bus from '../../common/bus';
    import axios from 'axios'
    import moment from 'moment'
    import {mapActions} from 'vuex'
    export default {
        name: 'baseform',
        data() {
            return {
                selectedTags: [],
                tag: [],
                tagCopy:[],
                newTag:[],
                primary:['','success','info','danger','warning'],
                gc: -1,
                selectedTagsCopy:[],
                staInfo: {
                    sta_name: '',
                    sta_addr: '',
                    sta_phone: '',
                    sta_notice: '',
                    sta_opentime:[new Date(2016, 9, 10, 8, 40), new Date(2016, 9, 10, 9, 40)]
                },
                inputVisible: false,
                inputValue: '',
                rules: {
                    sta_name: [
                        { required: true , message: '不能为空', trigger: "blur"}
                    ],
                    sta_addr: [
                        { required: true , message: '不能为空', trigger: "blur"}
                    ],
                    sta_phone: [
                        { required: true , message: '不能为空', trigger: "blur"}
                    ],
                    sta_opentime:[ { required: true , message: '不能为空', trigger: "blur"}]
                }
            };
        },
        beforeRouteEnter (to, from, next) {
            next(vm => {
                //查询已有标签
                vm.$service.get('/sms/getTags')
                    .then(function (data) {
                        vm.tag = data.data
                        vm.tagCopy = JSON.parse(JSON.stringify(data.data))
                    }).catch(function (error) {
                })
            })
        },
        mounted() {
            //获取修改信息
            let params = this.$route.params
            if (params){
                if (params.sta_startDayTime&&params.sta_endDayTime){
                    let start = params.sta_startDayTime.split(':')
                    let startH = parseInt(start[0])
                    let startM = parseInt(start[1])
                    let end = params.sta_endDayTime.split(':')
                    let endH = parseInt(end[0])
                    let endM = parseInt(end[1])
                    var sta_opentime = [new Date(2016, 9, 10, startH, startM), new Date(2016, 9, 10,endH, endM)]
                }
                this.staInfo = Object.assign({},params,{sta_opentime:sta_opentime})
                this.selectedTags = JSON.parse(JSON.stringify(params.selectedTags))
                this.selectedTagsCopy = JSON.parse(JSON.stringify(params.selectedTags))
            }

            //拖拽
            var els = document.getElementsByClassName('li')
            var arrOffset = []
            for(let i = 0; i<els.length ; i++) {
                arrOffset.push([els[i].offsetLeft,els[i].offsetTop])
            }
            Array.from(els).forEach(function(item, index, arr){
                item.style.position = 'absolute'
                item.style.left = arrOffset[index][0] + 'px'
                item.style.top = arrOffset[index][1] + 'px'
            })
        },
        methods: {
            ...mapActions([
                'commitSData',
            ]),
            getColor(index) {
                if (index > this.primary.length-1){
                    index = index%this.primary.length-1
                }
                return this.primary[index]
            },
            handleCloseSele(tag) {
                this.selectedTags.splice(this.selectedTags.indexOf(tag), 1);
                this.tag.push(tag);
                this.tag = Array.from(new Set(this.tag))
            },
            handleClose(tag) {
                this.tag.splice(this.tag.indexOf(tag), 1);
                this.newTag.splice(this.newTag.indexOf(tag),1)
            },

            showInput() {
                this.inputVisible = true;
                this.$nextTick(_ => {
                    this.$refs.saveTagInput.$refs.input.focus();
                });
            },
            handleInputConfirm() {
                let inputValue = this.inputValue;
                if (inputValue) {
                    if (!this.selectedTags.includes(inputValue) && !this.tag.includes(inputValue)){
                        this.tag.push(inputValue);
                        this.newTag.push(inputValue)
                    }
                }
                this.inputVisible = false;
                this.inputValue = '';
            },
            allowDrop:function (ev)
            {
                 ev.preventDefault();
            },
            drag:function (ev)
            {
                ev.dataTransfer.setData("Text",ev.target.innerText.trim());
                ev.dataTransfer.setData("className",ev.target.className);
            },
            drop:function (ev)
            {
                ev.preventDefault();
                var data=ev.dataTransfer.getData("Text");
                var classn = ev.dataTransfer.getData("className");
                var t =ev.target.id
                this[t].push(data)
                var index = this[classn].indexOf(data);
                if (index > -1) {
                    this[classn].splice(index, 1);
                }
            },
            submitData(staInfor) {
                let params = {}
                let _this = this
                _this.staInfo.deleteOldSeleTags = _this.selectedTagsCopy.filter(function(item) {
                    return _this.selectedTags.includes(item)?false:true
                })
                _this.staInfo.deleteOldTag = _this.tagCopy.filter(function(item) {
                    return (_this.tag.includes(item)||_this.newTag.includes(item)||_this.selectedTags.includes(item))?false:true
                })
                _this.staInfo.newSelectedTags = _this.selectedTags.filter(function(item) {
                    return _this.selectedTagsCopy.every(function(item1) {
                        return !(item1 == item)
                    })
                })
                _this.staInfo.selectedTags = _this.selectedTags
                _this.staInfo.newTag = _this.newTag
                _this.staInfo.sta_startDayTime = moment( _this.staInfo.sta_opentime[0]).format('HH:mm')
                _this.staInfo.sta_endDayTime = moment( _this.staInfo.sta_opentime[1]).format('HH:mm')
                this.$refs[staInfor].validate((valid) => {
                    if (valid) {
                        this.$service.post('/sms/createStaInfo',
                             JSON.stringify(_this.staInfo))
                            .then(function (data) {
                                console.log(data.data)
                             if (data.data == 'ok') {
                                 let changePath =  {
                                     name:'manageSta',
                                     path: '/manageSta',
                                     localName: _this.$route.fullPath}
                                 bus.$emit('changePath', changePath);
                                 _this.$router.push({name:'manageSta'})
                             }else if (data.data == 'updateOk') {
                                 let changePath =  {
                                     name:'staSpace',
                                     path: '/staSpace',
                                     localName: _this.$route.fullPath}
                                 bus.$emit('changePath', changePath);
                                 _this.commitSData(_this.staInfo);
                                 _this.$router.push({name:'staSpace'})
                             }
                             _this.$message.success('保存成功！');
                        }).catch(function (error) {
                            _this.$message.error('保存失败！');
                        })
                    } else {
                        _this.$message.warning('请填写带红*的必填项！');
                        return false;
                    }
                });
            },

            back() {
                let changePath =  {
                    name:'manageSta',
                    path: '/manageSta',
                    localName: this.$route.fullPath}
                bus.$emit('changePath', changePath);
                this.$router.push({name:'manageSta'})
            }
        }

        }


</script>
<style scoped>
    ul li{
        list-style: none;
        display: inline-block;
        margin: 5px;
    }

    .leftBox,.rightBox {
        min-width: 30rem;
        min-height: 5rem;
        border:1px dashed rosybrown;
    }
    .form-box1,.form-box2{
        width: 50%;
        margin: 0 auto;

    }
    /*.form-box2 {*/
    /*    width: 45%;*/
    /*    margin-left: 5%;*/
    /*    vertical-align: top;*/
    /*    font-size: 1.5rem;*/
    /*}*/

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }
    .button-new-tag {
        width: 7rem;
        height: 2rem;
        line-height: 2rem;
        float: right;
        font-family: 'PingFang SC', "Helvetica Neue", Helvetica, "microsoft yahei", arial, STHeiTi, sans-serif;
        vertical-align: center;
    }
    .input-new-tag {
        width: 90px;
        margin-left: 10px;
        vertical-align: bottom;
        float: right;
        padding: 3px 0;
        font-size: 10px;
    }
    .el-button--small, .el-button--small.is-round{
        padding: 0;
    }
    .shadow{
        box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    }
    table,table tr th, table tr td {
        min-width: 4rem;
        font-size: 16px;
        font-family: 'PingFang SC', "Helvetica Neue", Helvetica, "microsoft yahei", arial, STHeiTi, sans-serif;
        border:1px solid rgba(198, 60, 46, 0.12);
        color: rgba(236, 44, 255, 0.96);
    }
    table tr th {
        color: rgba(211, 14, 147, 0.65);
    }
    table {
        margin-top: 3rem;
        min-height: 25px;
        line-height: 25px;
        text-align: center;
        border-collapse: collapse;
    }
    .addTd {
        width: 33rem;
        border: 1px dashed rgba(211, 14, 147, 0.65);
        font-size: 16px;
        text-align: center;
        color:rgba(211, 14, 147, 0.65);
        margin-top: 20px;
        padding: 3px 0;
    }
    .scheduleContaniner{
        margin-top: 30px;
    }
    .line{
        text-align: center;
    }
    .btnGroup {
        text-align: center;
        margin: 50px;
    }
</style>
