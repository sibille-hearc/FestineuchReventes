<template>
        <div class="container">
        <h1 class="display-5">Ajouter une offre</h1>
        <hr>
        <div id="errorMsg" v-if="errorMsg==='ERROR'" class="alert alert-danger" role="alert">
            Erreur, les informations nécessaires pour la création ne sont pas correctes.
        </div>
        <div id="errorMsgSrv" v-if="errorMsg==='SRV_ERROR'" class="alert alert-danger" role="alert">
            Désolé, Une erreur est survenue lors de la création de l'offre. Nous avons été alertés du problème.
        </div>
        <div id="okMessage" v-if="errorMsg==='DONE'" class="alert alert-success" role="success">
            Votre offre a bien été enregistrée. Merci de votre contribution.
        </div>
        <div v-if="errorMsg!='DONE'">
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">Propriétés requises</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false">Propriétés optionnelles</a>
                </li>
            </ul>
            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                    <div class="form-group row">
                        <label for="nom" class="col-sm-2 col-form-label">Nom *</label>
                        <div class="col-sm-10">
                        <input type="text" v-model="nom" class="form-control" id="nom" placeholder="Nom">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="entreprise" class="col-sm-2 col-form-label">Entreprise *</label>
                        <div class="col-sm-10">
                        <input type="text" v-model="entreprise" class="form-control" id="entreprise" placeholder="Entreprise">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="description" class="col-sm-2 col-form-label">Description *</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" v-model="description" id="description" placeholder="Description détaillée de l'offre" rows="4"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                        <legend class="col-form-label col-sm-2 pt-0">Type de rabais *</legend>
                        <div class="col-sm-10">
                            <div class="form-check">
                            <input class="form-check-input" type="radio" name="rabaisRadios" id="reduction" value="réduction"  v-model="typeRabais">
                            <label class="form-check-label" for="reduction">
                                Réduction
                            </label>
                            </div>
                            <div class="form-check">
                            <input class="form-check-input" type="radio" name="rabaisRadios" id="prixfixe" value="prix fixe"  v-model="typeRabais">
                            <label class="form-check-label" for="prixfixe">
                                Prix fixe
                            </label>
                            </div>
                            <div class="form-check">
                            <input class="form-check-input" type="radio" name="rabaisRadios" id="pourcentage" value="pourcentage" v-model="typeRabais">
                            <label class="form-check-label" for="pourcentage">
                                Pourcentage
                            </label>
                            </div>
                            <div class="form-check">
                            <input class="form-check-input" type="radio" name="rabaisRadios" id="gratuit" value="gratuit" v-model="typeRabais">
                            <label class="form-check-label" for="gratuit">
                                Gratuit
                            </label>
                            </div>
                        </div>
                        </div>
                    </div>
                    <div class="form-group row" v-if="typeRabais=='réduction'">
                        <label for="entreprise" class="col-sm-2 col-form-label">Rabais en CHF *</label>
                        <div class="col-sm-10">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">CHF</span>
                                </div>
                                <input type="number" min="0.00" step="0.05" value="1.00" v-model="rabais" id="chfRabais" name="chfRabais" class="form-control" placeholder="Réduction en CHF">
                            </div>
                        </div>
                    </div>
                    <div class="form-group row" v-if="typeRabais=='prix fixe'">
                        <label for="entreprise" class="col-sm-2 col-form-label">Prix fixe en CHF *</label>
                        <div class="col-sm-10">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">CHF</span>
                                </div>
                                <input type="number" min="0.00" step="0.05" value="1.00" v-model="rabais" id="chfPrix" name="chfPrix" class="form-control" placeholder="Prix fixe en CHF">
                            </div>
                        </div>
                    </div>
                    <div class="form-group row" v-if="typeRabais=='pourcentage'">
                        <label for="entreprise" class="col-sm-2 col-form-label">Rabais en % *</label>
                        <div class="col-sm-10">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">%</span>
                                </div>
                                <input type="number" min="1" step="1" value="10" v-model="rabais" id="pourcRabais" name="pourcRabais" class="form-control" placeholder="Pourcentage de réduction">
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="interets" class="col-sm-2 col-form-label">Catégories *</label>
                        <div class="col-sm-10">
                            <multiselect name="interets"
                                v-model="selectedInterests"
                                :options="optionsInterests"
                                :multiple="true"
                                :close-on-select="true"
                                placeholder="Sélectionnez les catégories de l'offre">
                            </multiselect>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="dateDebut" class="col-sm-2 col-form-label">Début de validité *</label>
                        <div class="col-sm-10">
                            <datepicker v-model="dateDebut" name="dateDebut" :typeable="true" :bootstrap-styling="true"></datepicker>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="dateFin" class="col-sm-2 col-form-label">Fin de validité *</label>
                        <div class="col-sm-10">
                            <datepicker v-model="dateFin" name="dateFin" :typeable="true" :bootstrap-styling="true"></datepicker>
                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                                        <div class="form-group row">
                        <label for="ecole" class="col-sm-2 col-form-label">Ecole(s)</label>
                        <div class="col-sm-10">
                            <multiselect name="ecole"
                                v-model="selectedSchools"
                                :options="optionsSchools"
                                :multiple="true"
                                :allow-empty="false"
                                :close-on-select="true"
                                placeholder="Sélectionnez l'école qui propose l'offre">
                            </multiselect>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="cantons" class="col-sm-2 col-form-label">Cantons</label>
                        <div class="col-sm-10">
                            <multiselect name="cantons"
                                v-model="selectedCantons"
                                :options="optionsCantons"
                                :multiple="true"
                                :close-on-select="true"
                                placeholder="Sélectionnez les cantons où l'offre est diponible">
                            </multiselect>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="lien" class="col-sm-2 col-form-label">Lien</label>
                        <div class="col-sm-10">
                        <input type="text" v-model="lien" class="form-control" id="lien" placeholder="http://...">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="condition" class="col-sm-2 col-form-label">Condition supplémentaire</label>
                        <div class="col-sm-10">
                        <input type="text" v-model="condition" class="form-control" id="condition" placeholder="Condition supplémentaire de validité">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="ville" class="col-sm-2 col-form-label">Ville</label>
                        <div class="col-sm-10">
                        <input type="text" v-model="ville" class="form-control" id="ville" placeholder="Nom de la ville">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="condition" class="col-sm-2 col-form-label">Rue</label>
                        <div class="col-sm-7">
                        <input type="text" v-model="rue" class="form-control" id="condition" placeholder="Nom de rue">
                        </div>
                        <label for="condition" class="col-sm-1 col-form-label">Numéro</label>
                        <div class="col-sm-2">
                        <input type="number" v-model="numeroRue" class="form-control" id="numeroRue" placeholder="N°">
                        </div>
                    </div>
                </div>
            </div>
            <button class="btn btn-lg btn-primary btn-block" v-on:click="addOffer()">Ajouter l'offre</button>
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
import { required, url, integer } from 'vuelidate/lib/validators';
var qs = require('qs');
Vue.use(Vuelidate);
Vue.use(VueCookies);
export default {
    name: 'AjouterOffre',
    data() {
        return {
            errorMsg: null,
            nom: null,
            entreprise: null,
            description: null,
            dateDebut: null,
            dateFin: null,
            selectedCantons: [],
            optionsCantons: ['Neuchâtel', 'Jura', 'Vaud', 'Valais', 'Berne', 'Genève', 'Fribourg'],
            selectedInterests: [],
            optionsInterests: ['Transports', 'Divertissement', 'Musées', 'Logiciels', 'Autres'],
            selectedSchools: [],
            optionsSchools: ['HE-ARC', 'HEIG-VD', 'EPFL', 'UNINE', 'Autre'],
            typeRabais: null,
            rabais: 0,
            lien: null,
            condition: null,
            rue: null,
            numeroRue: null,
            integer: null,
            ville: null,
            dateDebutToSend: null,
            dateFinToSend: null
        };
    },
    components: {
        Datepicker,
        Multiselect
    },
    validations: {
        nom: {
            required
        },
        entreprise: {
            required
        },
        description: {
            required
        },
        typeRabais: {
            required
        },
        lien: {
            url
        },
        numeroRue: {
            integer
        }
    },
    methods: {
        addOffer() {
            this.$v.$touch();
            if (!this.$v.$invalid) {
                let that = this;
                if (this.dateDebut != null) {
                    this.dateDebutToSend = moment(this.dateDebut).format('DD/MM/YYYY');
                } else {
                    this.dateDebutToSend = '';
                }
                if (this.dateFin != null) {
                    this.dateFinToSend = moment(this.dateDebut).format('DD/MM/YYYY');
                } else {
                    this.dateFinToSend = '';
                }
                axios({
                    method: 'POST',
                    url: process.env.ROOT_PATH + '/offre/ajouter',
                    data: qs.stringify({
                        nom: this.nom,
                        entreprise: this.entreprise,
                        description: this.description,
                        dateDebut: this.dateDebutToSend,
                        dateFin: this.dateFinToSend,
                        cantons: this.selectedCantons.join(),
                        categories: this.selectedInterests.join(),
                        ecoles: this.selectedSchools.join(),
                        typeRabais: this.typeRabais,
                        montant: this.rabais,
                        lien: this.lien,
                        condition: this.condition,
                        rue: this.rue,
                        numeroRue: this.numeroRue,
                        ville: this.ville
                    }),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Accept': 'application/json',
                        'Authorization': 'Bearer ' + VueCookies.get('user_token')
                    }
                })
                .then(function (response) {
                    that.errorMsg = 'DONE';
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
    }
};
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>
<style>
#myTab {
    margin-bottom: 20px;
}
</style>
