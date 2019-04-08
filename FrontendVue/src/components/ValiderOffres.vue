<template>
    <div class="container">
        <h1 class="display-5">Validation des offres [ADMIN]</h1>
        <hr>
        <div class="row offres">
            <div class="col-md-4 offre" v-for="offresNonValidee in offresNonValidees" :key="offresNonValidee.id">
                <h4>{{offresNonValidee.entreprise}}</h4>
                <h2>{{offresNonValidee.nom}}</h2>
                <p>
                    {{offresNonValidee.description}}
                </p>
                <router-link :to="{name: 'OffreDetail', params: {id: offresNonValidee.id}}"><p><button class="btn btn-secondary btnValidate">Voir les détails afin de la valider &raquo;</button></p></router-link>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    name: 'ValiderOffres',
    data() {
        return {
            offresNonValidees: null
        };
    },
    mounted() {
        let that = this;
        axios({
            method: 'GET',
            url: process.env.ROOT_PATH + '/offre',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Accept': 'application/json'
            }
        })
        .then(function (response) {
            that.offresNonValidees = response.data.filter(offre => (offre.estValidee === '0'));
        })
        .catch(function (error) {
            console.log(error);
        });
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