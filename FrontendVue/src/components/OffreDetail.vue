<template>
    <div class="container">
        <div v-if="errorMsg==='OK'">
            <h1 class="display-5">Validation des offres [ADMIN]</h1>
            <hr>
        </div>
        <div id="errorMsgSRV" v-if="errorMsg==='SRV_ERROR'" class="alert alert-danger" role="alert">
            Désolé, Une erreur est survenue lors de la validation de l'offre. Nous avons été alertés du problème.
        </div>
        <div id="okMessage" v-if="errorMsg==='OK'" class="alert alert-success" role="success">
            L'offre a bien été validée.
        </div>
        <div id="okMessage" v-if="errorMsg==='DELETED'" class="alert alert-success" role="success">
            L'offre a bien été supprimée.
        </div>
        <div v-if="errorMsg!='OK'&&errorMsg!='DELETED'">
            <div class="offredetail" v-if="offreChoisie!=null">
            <h4>{{offreChoisie.entreprise}}</h4>
            <h2>{{offreChoisie.nom}}</h2>
            <div class="row">
                <div class="col-md-8">
                    <p>
                        <strong>Description :</strong><br>
                        {{offreChoisie.description}}<br><br>
                        <span v-if="categories!=null"><strong>Catégories</strong> : {{categories}}<br></span>
                        <span v-if="ecoles!=null"><strong>Ecoles partenaires</strong> : {{categories}}<br></span>
                        <span v-if="regions!=null"><strong>Régions</strong> : {{regions}}<br></span>
                    </p>
                    <div v-if="profile!=null&&profile.estAdministrateur===1">
                        <button v-if="offreChoisie.estValidee!='1'" class="btn btn-success" v-on:click="validate()">Valider l'offre</button>
                        <button class="btn btn-danger" v-on:click="deleteOffre()">Supprimer l'offre</button>
                    </div>
                </div>
                <div class="col-md-4">
                    <p>
                        <span v-if="offreChoisie.lien!=null"><strong>Lien de l'offre :</strong> {{offreChoisie.lien}}<br></span>
                        <span v-if="offreChoisie.dateDebut!=null"><strong>Date de début de validité :</strong> {{offreChoisie.dateDebut}}<br></span>
                        <span v-if="offreChoisie.dateFin!=null"><strong>Date de fin de validité :</strong> {{offreChoisie.datteFin}}<br></span>
                        <span v-if="offreChoisie.condition!=null"><strong>Condition supplémentaire de validité</strong> : {{offreChoisie.condition}}<br></span>
                        <span v-if="offreChoisie.typeRabais.description!=null"><strong>Type d'offre</strong> : {{offreChoisie.typeRabais.description}}<br></span>
                        <span v-if="offreChoisie.montant!=null"><strong>Rabais</strong> : <span v-if="offreChoisie.typeRabais.description=='réduction'">-</span>
                        {{offreChoisie.montant}}<span v-if="offreChoisie.typeRabais.description=='réduction'||offreChoisie.typeRabais.description=='prix fixe'"> CHF</span>
                        <span v-if="offreChoisie.typeRabais.description=='pourcentage'"> %</span><br></span>
                        <span v-if="offreChoisie.ville!=null"><strong>Ville</strong> : {{offreChoisie.ville}}<br></span>
                        <span v-if="offreChoisie.rue!='null'"><strong>Adresse</strong> : {{offreChoisie.rue}} {{offreChoisie.numeroRue}}<br></span>
                        <span v-if="offreChoisie.ville!=null"><strong>Ville</strong> : {{offreChoisie.ville}}<br></span>
                    </p>
                </div>
            </div>
            </div>
            <div class="offredetail container" v-else>
                <h2>Chargement en cours...</h2>
                Merci de patienter.
            </div>
        </div>
    </div>
</template>

<script>
import Vue from 'vue';
import VueCookies from 'vue-cookies';
import axios from 'axios';
var qs = require('qs');
Vue.use(VueCookies);
export default {
    name: 'OffreDetail',
    data() {
        return {
            offreChoisie: null,
            categories: null,
            ecoles: null,
            regions: null,
            profile: null,
            errorMsg: null
        };
    },
    methods: {
        deleteOffre() {
            let that = this;
            axios({
                method: 'DELETE',
                url: process.env.ROOT_PATH + '/offre?id=' + that.offreChoisie.id,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Accept': 'application/json',
                    'Authorization': 'Bearer ' + VueCookies.get('user_token')
                }
            })
            .then(function (response) {
                that.errorMsg = 'DELETED';
                that.offreChoisie = null;
            })
            .catch(function (error) {
                console.log(error);
                that.errorMsg = 'SRV_ERROR';
            }
            );
        },
        validate() {
            let that = this;
            axios({
                method: 'POST',
                url: process.env.ROOT_PATH + '/offre/verifier?id=' + that.offreChoisie.id,
                data: qs.stringify({
                    estvalidee: 1
                }),
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Accept': 'application/json',
                    'Authorization': 'Bearer ' + VueCookies.get('user_token')
                }
            })
            .then(function (response) {
                that.errorMsg = 'OK';
                that.offreChoisie = null;
            })
            .catch(function (error) {
                console.log(error);
                that.errorMsg = 'SRV_ERROR';
            }
            );
        }
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
            that.offreChoisie = response.data.find(p => String(p.id) === String(that.$route.params.id));
            that.categories = that.offreChoisie.categories.map(function (elem) {
                return elem.nom;
            }).join(',');
            that.ecoles = that.offreChoisie.partenaires.map(function (elem) {
                return elem.nom;
            }).join(',');
            that.regions = that.offreChoisie.regions.map(function (elem) {
                return elem.nom;
            }).join(',');
        })
        .catch(function (error) {
            console.log(error);
        }
        );

        if (VueCookies.get('user_token') != null) {
            that = this;
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
    
</style>