<template>
    <div class="container">
        <h1>Connexion</h1>
        <hr>
        <div>
            <div id="errorMsg" v-if="connectionStatus==='ERROR'" class="alert alert-danger" role="alert">
                Erreur, les informations de connexion fournies ne sont pas correctes.
            </div>
            <div id="errorMsgNotSent" v-if="connectionStatus==='NOT_SENT'" class="alert alert-danger" role="alert">
                Erreur, Veuillez remplir correctement le formulaire de connexion.
            </div>
            <div class="form-group row" :class="{ 'form-group--error': $v.email.error }">
                <label for="email" class="col-sm-2 col-form-label">Email *</label>
                <div class="col-sm-10">
                    <input :class="{ 'error': $v.email.invalid }" v-model="email" type="email" class="form-control" id="email" placeholder="Email">
                <div class="error" v-if="!$v.email.email">Veuillez entrer un email valide</div>
                </div>
            </div>
            <div class="form-group row" :class="{ 'form-group--error': $v.password.error }">
                <label for="password" class="col-sm-2 col-form-label">Mot de passe *</label>
                <div class="col-sm-10">
                    <input v-model="password" :class="{ 'error': $v.password.invalid }" type="password" class="form-control" id="password" placeholder="Mot de passe">
                </div>
            </div>
            <button class="btn btn-lg btn-primary btn-block" v-on:click="login()">Se connecter</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Vue from 'vue';
import Vuelidate from 'vuelidate';
import { email, required } from 'vuelidate/lib/validators';
import VueCookies from 'vue-cookies';
var qs = require('qs');
Vue.use(Vuelidate);
Vue.use(VueCookies);
export default {
    name: 'Connexion',
    data() {
        return {
            email: '',
            password: '',
            connectionStatus: null
        };
    },
    methods: {
        redirectLogged() {
            location.reload();
        },
        login() {
            this.$v.$touch();
            if (!this.$v.$error) {
                let that = this;
                axios({
                    method: 'POST',
                    url: process.env.ROOT_PATH + '/authentification',
                    data: qs.stringify({
                        email: this.email,
                        password: this.password
                    }),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Accept': 'application/json'
                    }
                })
                .then(function (response) {
                    console.log(response.data);
                    VueCookies.set('user_token', response.data, 60 * 60 * 24);
                    that.redirectLogged();
                })
                .catch(function (error) {
                    console.log(error);
                }
                );
            } else {
                this.connectionStatus = 'NOT_SENT';
            }
        }
    },
    validations: {
        password: {
            required
        },
        email: {
            email
        }
    }
};
</script>

<style>

</style>
