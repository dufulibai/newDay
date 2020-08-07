import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const store=new Vuex.Store({
    state:{
        staSpaceData:''
    },
    getters:{
    },
    mutations:{
        setSData(state,data){
            if (data){
                state.staSpaceData = data
            }
        }
    },
    actions:{
        commitSData(context,data){
            context.commit('setSData',data)
        }
    }
})

export default store
