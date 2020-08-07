<template>
    <div class="wrapper">
        <scrollTime></scrollTime>
        <div class="liner"></div>
        <div class="table">
            <schaduleTable></schaduleTable>
        </div>

<!--        {{scrollTime}}-->
    </div>

</template>

<script>
    import scrollTime from './scrollTime';
    import schaduleTable from './schaduleTable';
    import { mapState } from 'vuex'

    export default {
        name: 'spacePanel',
        data(){
            return{

            }
        },

        computed:{
            ...mapState({
                'spaceTempBysta':state => state.spaceTempBysta
            }),
        },
        mounted(){

        },
        methods:{

        },
        components:{
            scrollTime,
            schaduleTable
        }
    };
</script>
<style scoped>
@import "templatemo_style.css";
    .wrapper{
        margin: 0 auto;
    }

</style>
