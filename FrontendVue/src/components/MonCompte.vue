<template>
    <div class="container">
        <h1>Mon Compte</h1>
        <hr>
        <div>
            <div id="errorMsg" v-if="errorMsg==='ERROR'" class="alert alert-danger" role="alert">
                Erreur, les informations fournies ne sont pas correctes.
            </div>
            <div id="errorMsgSRV" v-if="errorMsg==='SRV_ERROR'" class="alert alert-danger" role="alert">
                Désolé, Une erreur est survenue à l'inscription. Nous avons été alertés du problème.
            </div>
            <div id="okMessage" v-if="errorMsg==='SAVED'" class="alert alert-success" role="success">
                Vos paramètres ont bien été enregistrés.
            </div>
            <div v-if="profile!=null&&profile.estAdministrateur===1">
                <h3>Administration</h3>
                <router-link :to="{ name: 'ValiderOffres', params: {}}"><a>Valider des offres</a></router-link><br>
                <hr>
            </div>
            <div class="form-group row">
                <label for="cantons" class="col-sm-2 col-form-label">Cantons d'intérêts</label>
                <div class="col-sm-10">
                    <multiselect name="cantons"
                        v-model="selectedCantons"
                        :options="optionsCantons"
                        :multiple="true"
                        :close-on-select="true"
                        placeholder="Sélectionnez votre ou vos cantons">
                    </multiselect>
                </div>
            </div>
            <div class="form-group row">
                <label for="interets" class="col-sm-2 col-form-label">Centres d'intérêts</label>
                <div class="col-sm-10">
                    <multiselect name="interets"
                        v-model="selectedInterests"
                        :options="optionsInterests"
                        :multiple="true"
                        :close-on-select="true"
                        placeholder="Sélectionnez vos intérêts">
                    </multiselect>
                </div>
            </div>
            <div class="form-group row">
                <label for="ecole" class="col-sm-2 col-form-label">Ecole *</label>
                <div class="col-sm-10">
                    <multiselect name="ecole"
                        v-model="selectedSchools"
                        :options="optionsSchools"
                        :multiple="true"
                        :allow-empty="false"
                        :close-on-select="true"
                        placeholder="Sélectionnez votre école">
                    </multiselect>
                </div>
            </div>
            <div class="checkbox">
                <label class="small">
                    <input v-model="notifications" name="notifications" type="checkbox"> J'aimerai recevoir des notifications par email à propos de nouvelles offres correspondant à mes intérêts
                </label>
            </div>
            <button class="btn btn-lg btn-primary btn-block" v-on:click="save()">Enregistrer</button>
        </div>
    </div>
</template>

<script>
import Multiselect from 'vue-multiselect';
import axios from 'axios';
import VueCookies from 'vue-cookies';
import Vue from 'vue';
import Vuelidate from 'vuelidate';
import { required } from 'vuelidate/lib/validators';
var qs = require('qs');
Vue.use(Vuelidate);
Vue.use(VueCookies);
export default {
    name: 'Inscription',
    data() {
        return {
            selectedCantons: [],
            optionsCantons: ['Neuchâtel', 'Jura', 'Vaud', 'Valais', 'Berne', 'Genève', 'Fribourg'],
            selectedInterests: [],
            optionsInterests: ['Transports', 'Divertissement', 'Musées', 'Logiciels', 'Autres'],
            selectedSchools: [],
            optionsSchools: ['HE-ARC', 'HEIG-VD', 'EPFL', 'UNINE', 'Autre'],
            notifications: false,
            errorMsg: '',
            profile: null
        };
    },
    components: {
        Multiselect
    },
    mounted() {
        let that = this;
        axios({
            method: 'GET',
            url: process.env.ROOT_PATH + '/profil',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Accept': 'application/json',
                'Authorization': 'Bearer ' + VueCookies.get('user_token')
            }
        })
        .then(function (response) {
            that.profile = response.data;
            that.selectedSchools = that.profile.ecoles.map(a => a.nom);
            that.selectedInterests = that.profile.categories.map(a => a.nom);
            that.selectedCantons = that.profile.cantons.map(a => a.nom);
            if (that.profile.nouvelleNotification === 'Y') {
                that.notifications = true;
            } else {
                that.notifications = false;
            }
        })
        .catch(function (error) {
            console.log(error);
        }
        );
    },
    methods: {
        save() {
            this.$v.$touch();
            if (!this.$v.$invalid) {
                let that = this;
                if (this.notifications === true) {
                    this.notificationsToSend = 'Y';
                } else {
                    this.notificationsToSend = 'N';
                }
                axios({
                    method: 'POST',
                    url: process.env.ROOT_PATH + '/profil/modifier',
                    data: qs.stringify({
                        notification: this.notificationsToSend,
                        cantons: this.selectedCantons.join(),
                        ecoles: this.selectedSchools.join(),
                        categories: this.selectedInterests.join()
                    }),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Accept': 'application/json',
                        'Authorization': 'Bearer ' + VueCookies.get('user_token')
                    }
                })
                .then(function (response) {
                    that.errorMsg = 'SAVED';
                    let that2 = that;
                    axios({
                        method: 'GET',
                        url: process.env.ROOT_PATH + '/offre',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                            'Accept': 'application/json'
                        }
                    })
                    .then(function (response) {
                        that2.$root.offres = response.data;
                    })
                    .catch(function (error) {
                        console.log(error);
                    }
                    );
                })
                .catch(function (error) {
                    console.log(error);
                    that.errorMsg = 'SRV_ERROR';
                });
            } else {
                this.errorMsg = 'ERROR';
            }
        }
    },
    validations: {
        selectedSchools: {
            required
        }
    }
};
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
<style>

</style>