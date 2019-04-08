<template>
    <div class="col-md-4 offre">
        <h4>{{value.entreprise}}</h4>
        <h2>{{value.nom}}</h2>
        <p>
            {{value.description}}
        </p>
        <router-link :to="{name: 'OffreDetail', params: {id: value.id}}"><p><button class="btn btn-secondary btnValidate">Voir les détails &raquo;</button></p></router-link>
    </div>
</template>

<script>
export default {
    name: 'Offres',
    props: ['value', 'index'],
    data() {
        return {
        };
    }
};
</script>

<style>
.offre {
    margin-bottom: 50px;
}
.btnValidate {
    position: absolute;
    bottom: -35px;
}
</style>

