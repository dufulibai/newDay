import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const store=new Vuex.Store({
    state:{
       staInfoTemp:{},
        spaceTempBysta:'',//tag and staNo
        scrollTimeDatesItem:'',
        spaceBystaNoTemp:'',//schadule spaNo staNo
        consumRecord:[],
        checkedRecord:[]
    },
    // getters: {
    //     // 单个参数
    //     getterScrollTimeDatesItem: function(state) {
    //         return state.scrollTimeDatesItem
    //     }
    // },
    mutations:{
        setStaInfoTemp(state,data){
            if (data){
                state.staInfoTemp = data
            }
        },
        spaceTempBysta(state,data){
            if (data){
                state.spaceTempBysta = data
            }
        },
        scrollTimeDatesItem(state,data){
            if (data){
                state.scrollTimeDatesItem = data
            }
        },
        spaceBystaNoTemp(state,data){
            if (data) {
                state.spaceBystaNoTemp = data
            }
        },
        setConsumRecord(state,data){
            if (data){
                state.consumRecord = data
            }
        },
        setCheckedRecord(state,data){
            state.checkedRecord = data
        }
    },
    actions:{
        commitStaInfoTemp(context,data){
            context.commit('setStaInfoTemp',data)
        },
        commitSpaceTempBysta(context,data){
            context.commit('spaceTempBysta',data)
        },
        commitScrollTimeDatesItem(context,data){
            context.commit('scrollTimeDatesItem',data)
        },
        commitSpaceBystaNoTemp(context,data){
            context.commit('spaceBystaNoTemp',data)
        },
        commitConsumRecord(context,data){
            context.commit('setConsumRecord',data)
        },
        commitCheckedRecord(context,data){
            context.commit('setCheckedRecord',data)
        }
    }
})

export default store
