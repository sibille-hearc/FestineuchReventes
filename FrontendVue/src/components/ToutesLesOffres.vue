<template>
    <div class="container">
        <h1 class="display-5">Toutes les offres disponibles</h1>
        <hr>
        <div class="row offres">
            <offres v-bind:value = "p" v-bind:index = "i" v-for="(p, i) in this.$root.offres" v-bind:key="i"></offres>
        </div>
    </div>
</template>

<script>
import Offres from './Offres';
export default {
    name: 'ToutesLesOffres',
    data() {
        return {
        };
    },
    components: {
        'offres': Offres
    }
};
</script>

<style>

</style>