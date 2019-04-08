<template>
    <div class="container">
        <h1>Inscription</h1>
        <hr>
        <div>
            <div id="errorMsg" v-if="errorMsg==='ERROR'" class="alert alert-danger" role="alert">
                Erreur, les informations fournies ne sont pas correctes.
            </div>
            <div id="errorMsgSRV" v-if="errorMsg==='SRV_ERROR'" class="alert alert-danger" role="alert">
                Désolé, Une erreur est survenue à l'inscription. Nous avons été alertés du problème.
            </div>
            <div class="form-group row">
                <label for="email" class="col-sm-2 col-form-label">Email</label>
                <div class="col-sm-10">
                    <input v-model="email" type="email" class="form-control" id="email" placeholder="Email">
                    <div class="error" v-if="!$v.email.email">Veuillez entrer un email valide</div>
                </div>
            </div>
            <div class="form-group row">
                <label for="password" class="col-sm-2 col-form-label">Mot de passe</label>
                <div class="col-sm-10">
                <input v-model="password" type="password" class="form-control" id="password" placeholder="Password">
                </div>
            </div>
            <div class="form-group row">
                <label for="nom" class="col-sm-2 col-form-label">Nom</label>
                <div class="col-sm-10">
                <input v-model="nom" type="text" class="form-control" id="nom" placeholder="Nom">
                </div>
            </div>
            <div class="form-group row">
                <label for="prenom" class="col-sm-2 col-form-label">Prénom</label>
                <div class="col-sm-10">
                    <input v-model="prenom" type="text" class="form-control" id="prenom" placeholder="Prenom">
                </div>
            </div>
            <div class="form-group">
                <div class="row">
                <legend class="col-form-label col-sm-2 pt-0">Sexe</legend>
                <div class="col-sm-10">
                    <div class="form-check">
                    <input class="form-check-input" type="radio" name="SexeRadios" id="homme" value="homme"  v-model="sexe">
                    <label class="form-check-label" for="homme">
                        Homme
                    </label>
                    </div>
                    <div class="form-check">
                    <input class="form-check-input" type="radio" name="SexeRadios" id="femme" value="femme" v-model="sexe">
                    <label class="form-check-label" for="femme">
                        Femme
                    </label>
                    </div>
                </div>
                </div>
            </div>
            <div class="form-group row">
                <label for="dateNaissance" class="col-sm-2 col-form-label">Date de naissance</label>
                <div class="col-sm-10">
                    <datepicker v-model="dateNaissance" name="dateNaissance" :typeable="true" format="dd/MM/yyyy" :bootstrap-styling="true"></datepicker>
                </div>
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
            <div class="checkbox">
                <label class="small">
                    <input v-model="tos" name="terms" type="checkbox"> J'ai lu et j'accepte les <router-link :to="{ name: 'ToS', params: {}}"><a>conditions générales d'utilisation</a></router-link>
                    <div class="error" v-if="$v.tos.$error">Vous devez accepter les conditions d'utilisations pour vous inscrire.</div>
                </label>
            </div>
            <button class="btn btn-lg btn-primary btn-block" v-on:click="register()">S'enregistrer</button>
        </div>
    </div>
</template>

<script>
import Datepicker from 'vuejs-datepicker';
import Multiselect from 'vue-multiselect';
import axios from 'axios';
import VueCookies from 'vue-cookies';
import Vue from 'vue';
import moment from 'moment';
import Vuelidate from 'vuelidate';
import { email, required } from 'vuelidate/lib/validators';
var qs = require('qs');
Vue.use(Vuelidate);
Vue.use(VueCookies);
export default {
    name: 'Inscription',
    data() {
        return {
            dateNaissance: new Date(),
            selectedCantons: [],
            optionsCantons: ['Neuchâtel', 'Jura', 'Vaud', 'Valais', 'Berne', 'Genève', 'Fribourg'],
            selectedInterests: [],
            optionsInterests: ['Transports', 'Divertissement', 'Musées', 'Logiciels', 'Autres'],
            selectedSchools: [],
            optionsSchools: ['HE-ARC', 'HEIG-VD', 'EPFL', 'UNINE', 'Autre'],
            email: '',
            password: '',
            nom: '',
            prenom: '',
            sexe: null,
            notifications: false,
            tos: false,
            errorMsg: '',
            notificationsToSend: null,
            sexeToSend: null
        };
    },
    components: {
        Datepicker,
        Multiselect
    },
    methods: {
        redirectLogged() {
            location.reload();
        },
        register() {
            this.$v.$touch();
            if (!this.$v.$invalid) {
                let that = this;
                if (this.notifications === true) {
                    this.notificationsToSend = 'Y';
                } else {
                    this.notificationsToSend = 'N';
                }
                if (this.sexe === 'femme') {
                    this.sexeToSend = 'Y';
                } else {
                    this.sexeToSend = 'N';
                }
                axios({
                    method: 'POST',
                    url: process.env.ROOT_PATH + '/profil/creer',
                    data: qs.stringify({
                        email: this.email,
                        password: this.password,
                        nom: this.nom,
                        prenom: this.prenom,
                        sexe: this.sexeToSend,
                        notification: this.notificationsToSend,
                        dateNaissance: moment(this.dateNaissance).format('DD/MM/YYYY'),
                        cantons: this.selectedCantons.join(),
                        ecoles: this.selectedSchools.join(),
                        categories: this.selectedInterests.join()
                    }),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Accept': 'application/json'
                    }
                })
                .then(function (response) {
                    VueCookies.set('user_token', response.data, 60 * 60 * 24);
                    that.redirectLogged();
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
        password: {
            required
        },
        email: {
            email
        },
        dateNaissance: {
            required
        },
        selectedSchools: {
            required
        },
        nom: {
            required
        },
        prenom: {
            required
        },
        sexe: {
            required
        },
        tos: {
            checked: value => value === true
        }
    }
};
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
<style>

</style>