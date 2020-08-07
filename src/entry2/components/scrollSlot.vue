<template>
    <div :class="'outer-container'+data.select">
        <div :class="'inner-container'+data.select">
            <div :class="'content'+data.select">
                <slot></slot>
            </div>
        </div>
    </div>
</template>

<script>
    import $ from 'jquery'
    export default {
        name: 'scrollSlot',
        props:['data'],
    };
</script>

<style scoped>
    .outer-container2 {
        width: 150px; height: 4rem;
    }
    .content2{
        min-width: 150px; min-height: 4rem;
    }
    .outer-container2 {
        overflow: hidden;
        /*transform: translateX(200);*/
    }
    .inner-container2 {
        width: 300px;
        height:7rem;
        overflow-x: hidden;
        overflow-y: scroll;
    }
</style>
