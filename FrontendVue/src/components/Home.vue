<template>
<div class="pageHome">
        <div class="jumbotron">
            <div class="container">
            <h1>Toutes les offres réservées aux étudiants suisses !</h1>
            <p>
                Notre site web permet d'avoir accès à toutes les offres pour étudiants suisses et donc de 
                bénéficier des meilleurs prix avec votre carte. 
                Si vous vous inscrivez et renseignez ses informations personnelles comme le lieu 
                d'étude, ou les cantons de préférence. Nous vous informerons ensuite automatiquement par email des offres qui correspondent à votre profil.
                Il est aussi possible de faire une recherche générale indépendamment du profil utilisateur.
            </p>
            <router-link :to="{ name: 'ToutesLesOffres', params: {}}"><p><a class="btn btn-primary btn-lg" href="#" role="button">Voir toutes les offres &raquo;</a></p></router-link>
            </div>
        </div>
        <div class="container">
            <h1 class="display-5" v-if="isLogged===true">Sélection personnalisée</h1>
            <h1 class="display-5" v-else>Meilleures offres</h1>
            <hr>
            <div class="row offres">
                <offres v-bind:value = "p" v-bind:index = "i" v-for="(p, i) in offresPerso" v-bind:key="i"></offres>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Offres from './Offres';
import Vue from 'vue';
import VueCookies from 'vue-cookies';
Vue.use(VueCookies);
export default {
    name: 'Home',
    data() {
        return {
            offresPerso: null
        };
    },
    components: {
        'offres': Offres
    },
    mounted() {
        if (this.isLogged === true) {
            let that = this;
            axios({
                method: 'GET',
                url: process.env.ROOT_PATH + '/offre/personnalise',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Accept': 'application/json',
                    'Authorization': 'Bearer ' + VueCookies.get('user_token')
                }
            })
            .then(function (response) {
                that.offresPerso = response.data;
                that.offresPerso = response.data.filter(offre => (offre.estValidee === '1'));
            })
            .catch(function (error) {
                console.log(error);
            }
            );
        } else {
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
                that.offresPerso = response.data.filter(offre => (offre.estValidee === '1'));
                that.offresPerso = that.offresPerso.slice(0, 3);
            })
            .catch(function (error) {
                console.log(error);
            }
            );
        }
    }
};
</script>

<style>
    .jumbotron {
        margin-top: -60px;
        padding-bottom: 15px!important;
    }
</style>
