<template>
    <div class="staInfo">
        <div class="contain">
        <el-tabs :tab-position="tabPosition" style="min-height: 100%;">
            <el-tab-pane label="场馆信息">
                <el-row>
                    <el-button type="primary" @click="editSta">修改场馆信息</el-button>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="6"><div class="grid-content bg-purple">场馆名称:<span>{{getstaSpaceData.sta_name}}</span></div></el-col>
                    <el-col :span="6"><div class="grid-content bg-purple">场馆地址:<span>{{getstaSpaceData.sta_addr}}</span></div></el-col>
                    <el-col :span="6"><div class="grid-content bg-purple">场馆电话:<span>{{getstaSpaceData.sta_phone}}</span></div></el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="6"><div class="grid-content bg-purple">场馆公告:<span>{{getstaSpaceData.sta_notice}}</span></div></el-col>
                    <el-col :span="6"><div class="grid-content bg-purple">创建场馆时间:<span>{{getstaSpaceData.sta_createTime}}</span></div></el-col>
                    <el-col :span="6"><div class="grid-content bg-purple">营业时间:<span>{{getstaSpaceData.sta_startDayTime}}-{{getstaSpaceData.sta_endDayTime}}</span></div></el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <div class="grid-content bg-purple">标签:<el-tag type="success" v-for="(item,index) in getstaSpaceData.selectedTags" :key="index" style="margin: 0.5rem 0.5rem;">{{item}}</el-tag></div>
                    </el-col>
                </el-row>
            </el-tab-pane>
            <el-tab-pane label="运营时间管理">
                <div style="text-align: left;margin: 20px;" v-show="schadule.length>0">
                    <el-row>
                        <el-button size="medium" @click="saveSchadule" type="primary">保存</el-button>
                    </el-row>
                </div>
                    <div  v-for="(item,index) in schadule" :key="index" style="height: auto;">
                        <div class="divider"><div class="divider_text is_left" @click="deleteTimeItem(index)">删除时刻表</div></div>
                        <div style="margin: 20px;">
                        <el-row style="margin-top: 20px;">
                            <el-col :span="2" :offset="1">时间表名称：</el-col>
                            <el-col :span="6">
                                <el-input v-model="item.scha_name" placeholder="请输入名称"></el-input>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="2" :offset="1">营业时间：</el-col>
                            <el-col :span="6">
                                <el-time-picker
                                        is-range
                                        v-model="item.spa_opentime"
                                        range-separator="至"
                                        start-placeholder="开始时间"
                                        end-placeholder="结束时间"
                                        placeholder="选择时间范围"
                                >
                                </el-time-picker>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="2" :offset="1">单价：</el-col>
                            <el-col :span="3">
                                <el-input-number v-model="item.scha_priceTime" :precision="2" :step="0.1" :max="10"></el-input-number>
                            </el-col>
                            <el-col :span="2">小时(单位)</el-col>
                            <el-col :span="3">
                                <el-input-number v-model="item.scha_price"></el-input-number>
                            </el-col>
                            <el-col class="line" :span="0.5">元(单位)</el-col>
                        </el-row>
                        </div>
                   </div>
                <div class="divider"><div class="divider_text is_left" @click="addTime">+添加时刻表</div></div>
                <div>
                    <el-button type="danger" style="margin-left: 3rem;" @click="pitchDelete">批量删除</el-button>
                    <table class="schaTable">
                        <tr>
                            <th><el-checkbox  v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox></th>
                            <th>时刻表名</th>
                            <th>开始营业时间</th>
                            <th>结束营业时间</th>
                            <th>单价（时/元）</th>
                            <th><el-input
                                    v-model="search"
                                    size="mini"
                                    placeholder="输入关键字搜索"/>
                            </th>
                        </tr>
                        <tr v-for="(item,index) in searchSchaTable" :key="index">
<!--                            //v-model="item.checked" (item,index)-->
                            <td><el-checkbox   @change="handleCheckedChange(index)">{{index+1}}</el-checkbox></td>
                            <td>{{item.scha_name}}</td>
                            <td>{{item.scha_startTime}}</td>
                            <td>{{item.scha_endTime}}</td>
                            <td>{{item.unitPrice}}</td>
                            <td><span @click="deleteSchaRowData(item,index)">删除</span> | <span @click="editRowData(item,index)">编辑</span></td>
                        </tr>
                    </table>
                </div>
                <el-dialog
                        title="编辑"
                        :visible.sync="dialogVisible"
                        width="50%"
                        :before-close="handleClose">
                    <el-row style="margin: 0 0 1rem 0;">
                        <el-col :span="3" :offset="1">时间表名称：</el-col>
                        <el-col :span="6">
                            <el-input v-model="editItem.scha_name" placeholder="请输入名称"></el-input>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="3" :offset="1">营业时间：</el-col>
                        <el-col :span="6">
                            <el-time-picker
                                    is-range
                                    v-model="editItem.spa_opentime"
                                    range-separator="至"
                                    start-placeholder="开始时间"
                                    end-placeholder="结束时间"
                                    placeholder="选择时间范围"
                            >
                            </el-time-picker>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="3" :offset="1">单价：</el-col>
                        <el-col :span="4">
                            <el-input-number v-model="editItem.scha_priceTime" :precision="2" :step="0.1" :max="10"></el-input-number>
                        </el-col>
                        <el-col :span="3">小时(单位)</el-col>
                        <el-col :span="4">
                            <el-input-number v-model="editItem.scha_price"></el-input-number>
                        </el-col>
                        <el-col class="line" :span="0.5">元(单位)</el-col>
                    </el-row>
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveEditSchaData">保存修改</el-button>
                </el-dialog>
            </el-tab-pane>
            <el-tab-pane label="场地创建">
                <el-row>
                    <el-col :span="4" :offset="1"><div style="font-weight:800;color: rgba(14,18,33,0.98);display: inline-block;">启用标签(创建场地)：</div></el-col>
                    <el-col :span="19">
                        <el-checkbox-group v-model="checkedTags" @change="handleCheckedTags">
                            <el-checkbox style="margin-top:0.2rem;color: #110212;" v-for="(item,index) in getstaSpaceData.selectedTags" :key="index" :label="item"></el-checkbox>
                        </el-checkbox-group>
                    </el-col>
                </el-row>
                <el-row v-show="checkedTags.length>0">
                    <el-col :span="24" :offset="1" style="margin-top: 0.5rem;">
                        <el-button type="primary" @click="saveStaSpace">保存</el-button>
                    </el-col>
                </el-row>
                <div v-show="checkedTags.includes(item)" v-for="(item,index) in checkedTags" :key="index">
                    <el-row>
                        <el-col :span="2" :offset="1">
                            <div style="font-weight:800;color: rgba(14,18,33,0.98);padding-left:1rem;border-left:4px solid #2120ff;">{{item}}</div>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="2" :offset="2"><div style="font-weight:800;color: rgba(14,18,33,0.98);display: inline-block;">启用时刻表：</div></el-col>
                        <el-col :span="20">
                            <el-select v-model="spaceInfo1[index].schaNo" placeholder="请选择" @change="changeScha(index)">
                                <el-option
                                        v-for="i in schaduleTable"
                                        :key="i.schaNo"
                                        :label="i.scha_name"
                                        :value="i.schaNo">
                                </el-option>
                            </el-select>
                        </el-col>
                    </el-row>
                    <el-row>
                        <table>
                            <tr>
                                <th>场馆名称</th>
                                <th>时刻表</th>
                                <th>删除</th>
                            </tr>
                            <tr v-for="(item1,index1) in spaceInfo1[index].spaces" :key="index1">
                                <td><el-input v-model="item1.spaName" placeholder="请输入内容"></el-input></td>
                                <td>
                                    <el-select v-model="item1.schaNo" placeholder="请选择">
                                        <el-option
                                                v-for="item2 in schaduleTable"
                                                :key="item2.schaNo"
                                                :label="item2.scha_name"
                                                :value="item2.schaNo">
                                        </el-option>
                                    </el-select>
                                </td>
                                <td @click="deleteRowData(index,index1)"><i class="el-icon-remove-outline"></i></td>
                            </tr>
                        </table>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <div class="createSpaceBtn"  @click="createStaSpace(spaceInfo1[index])">+创建场地</div>
                        </el-col>
                    </el-row>
                </div>
                <div>
                    <el-button type="danger" style="margin-left: 3rem;" @click="pitchDeleteSpa">批量删除</el-button>
                    <table class="schaTable">
                        <tr>
                            <th><el-checkbox  v-model="checkAllSpa" @change="handleCheckAllSpaChange">全选</el-checkbox></th>
                            <th>场地名称</th>
                            <th>标签</th>
                            <th>时刻表</th>
                            <th><el-input
                                    v-model="searchSpa"
                                    size="mini"
                                    placeholder="输入关键字搜索"/>
                            </th>
                        </tr>
                        <tr v-for="(item,index) in searchSpaTable" :key="index">
                        <td><el-checkbox v-model="item.checked" @change="handleCheckedSpaChange(item)">{{index+1}}</el-checkbox></td>
                        <td>{{item.spaName}}</td>
                        <td>{{item.tag}}</td>
                        <td>{{item.scha_name}}</td>
                        <td><span @click="deleteSpaRowData(item,index)">删除</span> | <span @click="editSpaRowData(item)">编辑</span></td>
                        </tr>
                    </table>
                </div>
                <el-dialog
                        title="编辑"
                        :visible.sync="dialogVisibleSpa"
                        width="50%"
                        :before-close="handleClose">
                    <el-row>
                        <el-col :span="2" :offset="2">
                            场地名称:
                        </el-col>
                        <el-col :span="16">
                           <el-input v-model="editSpaItem.spaName" placeholder="请输入内容"></el-input>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="2" :offset="2">
                            标签:
                        </el-col>
                        <el-col :span="16">
                            <el-select v-model="editSpaItem.tag" placeholder="请选择">
                                <el-option
                                        v-for="item in getstaSpaceData.selectedTags"
                                        :key="item"
                                        :label="item"
                                        :value="item">
                                </el-option>
                            </el-select>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="2" :offset="2">时刻表:</el-col>
                        <el-col :span="16">
                        <el-select v-model="editSpaItem.schaNo" placeholder="请选择">
                            <el-option
                                    v-for="item in schaduleTable"
                                    :key="item.schaNo"
                                    :label="item.scha_name"
                                    :value="item.schaNo">
                            </el-option>
                        </el-select>
                        </el-col>
                    </el-row>
                    <el-button @click="dialogVisibleSpa = false">取 消</el-button>
                    <el-button type="primary" @click="saveEditSpaData">保存修改</el-button>
                </el-dialog>
            </el-tab-pane>
        </el-tabs>
        </div>
        <div class="btnGroup">
            <el-row>
                <el-button size="medium" @click="back">返回</el-button>
            </el-row>
        </div>
    </div>


</template>

<script>
    import bus from '../../common/bus';
    import { mapState } from 'vuex'
    import moment from 'moment'
    export default {
        name: 'staSpace',
        data() {
            return {
                dialogVisible: false,
                dialogVisibleSpa: false,
                search: '',
                searchSpa:'',
                props: { multiple: true },
                tabPosition: 'left',
                schadule: [],
                schaduleTable: [],
                spaceTable:[],
                isIndeterminate: true,
                checkAll: false,
                checkAllSpa: false,
                checkedSchas: [],
                checkedSpas:[],
                editItem:{},
                editSpaItem:{},
                checkedTags:[],
                spaceInfo1:[]
            }
        },
        mounted() {
             this.querySchaData()
             this.querySpaceData()
        },
        computed: {
            getstaSpaceData() {
                return this.staSpaceData
            },
            ...mapState({
                staSpaceData: state => state.staSpaceData
            }),
            searchSchaTable() {
                if (this.schaduleTable && this.schaduleTable.length > 0) {
                    if (this.search) {
                        return this.schaduleTable.filter(data => {
                            for (let key in data) {
                                if (Object.prototype.toString.call(data[key]) == "[object String]" && data[key].toLowerCase().includes(this.search.toLowerCase())) {
                                    return true
                                }
                            }
                        })
                    }

                }
                return this.schaduleTable
            },
            searchSpaTable(){
                if (this.spaceTable && this.spaceTable.length > 0) {
                    if (this.searchSpa) {
                        return this.spaceTable.filter(data => {
                            for (let key in data) {
                                if (Object.prototype.toString.call(data[key]) == "[object String]" && data[key].toLowerCase().includes(this.searchSpa.toLowerCase())) {
                                    return true
                                }
                            }
                        })
                    }

                }
                return this.spaceTable
            }
        },
        methods: {
            editSta(){
                let changePath =  {
                    name:'createStaForm',
                    path: '/createStaForm',
                    localName: this.$route.fullPath}
                bus.$emit('changePath', changePath);
                this.$router.push({ name: 'createStaForm', params: this.getstaSpaceData})
            },
            changeScha(index){
                let _this = this
                this.spaceInfo1[index].spaces.forEach(function(item) {
                    item.schaNo = _this.spaceInfo1[index].schaNo
                })
            },
            handleCheckedTags(){
                let _this = this
                console.log('checkedTags')
                console.log(_this.checkedTags)
                console.log(_this.spaceInfo1)
                if(_this.checkedTags.length>0){
                    _this.checkedTags.forEach(function(item) {
                        if (_this.checkedTags.length>_this.spaceInfo1.length){
                            if (_this.spaceInfo1.length>0){
                                var isEqual = _this.spaceInfo1.every(function(item1) {
                                    return item1.tag == item
                                })
                            }
                            if (!isEqual || _this.spaceInfo1.length<=0) {
                                _this.spaceInfo1.push({
                                    tag:item,
                                    schaNo:'',
                                    staNo:_this.getstaSpaceData.staNo,
                                    spaces:[]
                                })
                            }
                        } else if (_this.checkedTags.length<_this.spaceInfo1.length){
                            _this.spaceInfo1 = _this.spaceInfo1.filter(function(item1) {
                                return item1.tag == item
                            })
                        }
                    })
                } else {
                    _this.spaceInfo1.splice(0)
                }

            },
            saveEditSchaData(){
                let _this = this
                this.editItem.scha_startTime = moment(this.editItem.spa_opentime[0]).format("HH:mm")
                this.editItem.scha_endTime = moment(this.editItem.spa_opentime[1]).format("HH:mm")
               this.$service.post('/sms/editSchaData',JSON.stringify(this.editItem))
                   .then(function(data) {
                       _this.$message.success("修改成功！")
                       _this.dialogVisible = false
                       _this.querySchaData()
                   }).catch(function(error) {
                   _this.$message.error("修改失败！")
               })
            },
            saveEditSpaData(){
                let _this = this
                this.$service.post('/sms/editSpaData',JSON.stringify(this.editSpaItem))
                    .then(function(data) {
                        _this.$message.success("修改成功！")
                        _this.dialogVisibleSpa = false
                        _this.querySpaceData()
                    }).catch(function(error) {
                    _this.$message.error("修改失败！")
                })
            },
            editRowData(item,index){
                let start = item.scha_startTime.split(':')
                let startH = parseInt(start[0])
                let startM = parseInt(start[1])
                let end = item.scha_endTime.split(':')
                let endH = parseInt(end[0])
                let endM = parseInt(end[1])
                this.editItem = Object.assign({},{spa_opentime:[new Date(2016, 9, 10,startH , startM), new Date(2016, 9, 10, endH, endM)]},item)
                this.dialogVisible = true
            },
            editSpaRowData(item){
                this.editSpaItem = Object.assign({},item)
                this.dialogVisibleSpa = true
            },
            querySchaData(){
                let param = {
                    staNo: this.getstaSpaceData.staNo
                }
                let _this = this
                this.$service.post('/sms/getAllScha', param)
                    .then(function(data) {
                        _this.schaduleTable = data.data
                        _this.schaduleTable.forEach(function(item) {
                            item.checked = false
                            item.unitPrice = item.scha_priceTime + ' 时/ ' + item.scha_price + ' 元'
                        })
                        console.log(_this.schaduleTable)
                    }).catch(function(error) {
                })
            },
            querySpaceData(){
                let param = {
                    staNo: this.getstaSpaceData.staNo
                }
                let _this = this
                this.$service.post('/sms/getAllSpace', param)
                    .then(function(data) {
                        _this.spaceTable.splice(0)
                        data.data.forEach(function(item) {
                            item.spaces.forEach(function(item1) {
                                let temp = {}
                                temp.tag = item.tag
                                temp.staNo =item.staNo
                                temp.checked = false
                                Object.assign(temp,item1)
                                let scha = _this.schaduleTable.filter(function(item2) {
                                    return item2.schaNo == item1.schaNo
                                })
                                temp.scha_name = scha[0].scha_name
                                _this.spaceTable.push(temp)
                            })
                        })
                    }).catch(function(error) {
                })
            },
            pitchDeleteSpa(){
                let _this = this
                this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$service.post('/sms/pitchDeleteSpa',
                        JSON.stringify(_this.checkedSpas))
                        .then(function(data) {
                            _this.querySpaceData()
                            _this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }).catch(function(error) {
                    })

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            pitchDelete(){
                let _this = this
                let newCheckedSchas = []
                for( let item of _this.checkedSchas) {
                    if (!newCheckedSchas.includes(item)) {
                        newCheckedSchas.push(item)
                    }
                }
                this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$service.post('/sms/pitchDelete',
                        JSON.stringify(newCheckedSchas))
                        .then(function(data) {
                            _this.querySchaData()
                            _this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }).catch(function(error) {
                    })

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            handleCheckedChange(index) {

                this.searchSchaTable[index].checked = !this.searchSchaTable[index].checked
                if(this.searchSchaTable[index].checked) {

                    this.checkedSchas.push(this.searchSchaTable[index].schaNo)
                }else{
                    this.checkedSchas.splice(this.checkedSchas.indexOf(this.searchSchaTable[index].schaNo),1)
                }
                this.checkAll = false;
                this.isIndeterminate = false;
            },
            handleCheckedSpaChange(value) {
                if(value.checked) {
                    this.checkedSpas.push(value.spaNo)
                }else{
                    this.checkedSpas.splice(this.checkedSpas.indexOf(value.spaNo),1)
                }
                this.checkAllSpa = false;
            },
            handleCheckAllChange(val) {
                let _this = this
                if (val) {
                    this.searchSchaTable.forEach(function(item) {
                        item.checked = true
                        if (!_this.checkedSchas.includes(item.schaNo)){
                            _this.checkedSchas.push(item.schaNo)
                        }

                    })
                    _this.isIndeterminate = false;
                } else {
                    this.searchSchaTable.forEach(function(item) {
                        item.checked = false
                        _this.checkedSchas.splice(0)
                        _this.isIndeterminate = true;
                    })

                }
            },
            handleCheckAllSpaChange(val) {
                let _this = this
                if (val) {
                    this.searchSpaTable.forEach(function(item) {
                        item.checked = true
                        if (!_this.checkedSpas.includes(item.spaNo)){
                            _this.checkedSpas.push(item.spaNo)
                        }
                    })
                } else {
                    this.searchSpaTable.forEach(function(item) {
                        item.checked = false
                        _this.checkedSpas.splice(0)
                    })

                }
            },
            deleteSpaRowData(item, index){
                let _this = this
                this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$service.post('/sms/deleteSpaData',
                        JSON.stringify({ spaNo: item.spaNo }))
                        .then(function(data) {
                            _this.spaceTable.splice(index, 1)
                            _this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }).catch(function(error) {
                    })

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
        deleteSchaRowData(item, index) {
            let _this = this
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$service.post('/sms/deleteSchaData',
                    JSON.stringify({ schaNo: item.schaNo }))
                    .then(function(data) {
                        _this.schaduleTable.splice(index, 1)
                        _this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                    }).catch(function(error) {
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        saveSchadule() {
            let isEmpty = this.validSpaceInfo1Empty(this.schadule)
            if (isEmpty) {
                this.$message.warning('请将信息填写完整！')
                return
            }
            let _this = this
            this.schadule.forEach(function(item) {
                item.scha_startTime = moment(item.spa_opentime[0]).format('HH:mm')
                item.scha_endTime = moment(item.spa_opentime[1]).format('HH:mm')
                item.staNo = _this.getstaSpaceData.staNo
            })

            this.$service.post('/sms/createSchadule',
                JSON.stringify(this.schadule))
                .then(function(data) {
                    _this.schadule.splice(0)
                    data.data.forEach(function(item) {
                        item.unitPrice = item.scha_priceTime + ' 时/ ' + item.scha_price + ' 元'
                    })
                    _this.schaduleTable = [..._this.schaduleTable, ...data.data]
                    _this.$message.success('保存成功！');
                }).catch(function(error) {
            })
        },
        deleteTimeItem(index) {
            this.schadule.splice(index, 1)
        },
        deleteRowData(index,index1) {
            this.spaceInfo1[index].spaces.splice(index1, 1)
        },
        saveStaSpace() {
            let isEmpty = this.validSpaceInfo1Empty(this.spaceInfo1)
            let _this = this
            if (isEmpty) {
                this.$message.warning('请将信息填写完整！')
                return
            }
            this.$service.post('/sms/createSpaceInfo',
                JSON.stringify(this.spaceInfo1))
                .then(function(data) {
                    _this.querySpaceData()
                    _this.checkedTags.splice(0)
                    _this.spaceInfo1.splice(0)
                    _this.$message.success('保存成功！');
                }).catch(function(error) {
            })
        },
        validSpaceInfo1Empty(spaceInfo1){
            return spaceInfo1.some(function(item) {
                for (let op in item){
                    if (!item[op]){
                        return true
                    }
                }
            })
        },
        validEmpty(spaceInfo) {
            let result = spaceInfo.some(function(item) {
                return item.spaces.some(function(i) {
                    for (let op in i){
                        if (!i[op]){
                            return true
                        }
                    }
                })
            })
            return result
        },
        addTime() {
            this.schadule.push({
                scha_name: '',
                spa_opentime: [new Date(2016, 9, 10, 8, 0), new Date(2016, 9, 10, 18, 0)],
                scha_priceTime: '',
                scha_price: '',
            })
        },
        createStaSpace(item) {
            item.spaces.push({
                spaName: '',
                schaNo: item.schaNo
            })
        },
        back() {
            let changePath = {
                name: 'manageSta',
                path: '/manageSta',
                localName: this.$route.fullPath
            }
            bus.$emit('changePath', changePath);
            this.$router.push({ name: 'manageSta' })
        },
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {});
        }
    }
    }
</script>

<style>
    .staInfo{
      background: #fff;
        width: 100%;
        min-height: 100%;
        height:auto;
        padding-top: 2rem;
        padding-left: 2rem;
    }
    .contain{
        width: 100%;
        height: 100%;
    }
    .btnGroup {
        text-align: center;
        margin-top:1rem;
        margin-bottom:1rem;
    }
.bg-purple {
color: #740196;
line-height:36px ;
text-align: left;
padding-left: 2rem;
background: rgba(198, 116, 149, 0.02);
}
.grid-content {
border-radius: 4px;
min-height: 36px;
}
.bg-purple>span{
margin-left: 1rem;
}
table tr th, table tr td {
min-width:10rem; border:1px solid #0094ff;
}
table {  min-height: 25px; line-height: 25px; text-align: center; border-collapse: collapse;margin-top: 1rem; margin-left: 7rem;}
.createSpaceBtn{
width: 20rem;
height: 25px;
border: 1px dashed #0094ff;
text-align: center;
margin-left: 7rem;
cursor: pointer;
color: #0094ff;
}
.createSpaceBtn:hover{
background: #0094ff;
color: #fff;
}
.divider{
box-sizing: content-box;
height: 1px;
width: 80%;
padding: 24px 0;
background-clip:content-box;
background-color: #DCDFE6;
position: relative;
border-bottom: 1px solid transparent;
}
.divider_text{
position: absolute;
background-color: #FFF;
padding: 0 20px;
color: #d36386;
cursor: pointer;
}
.divider_text:hover{
color: #d30717;
}
.is_left{
left: 20px;
transform: translateY(-50%);
}
    .schaTable td:last-child span{
        color: #0094ff;
    }
    .schaTable td:last-child span:hover{
        text-decoration: underline;
        color: red;
        cursor: pointer;
    }
    .el-row {
        margin: 0 0 1rem 0
    }
</style>
